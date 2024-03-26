package com.ahold.ctp.sandbox.service

import com.ahold.ctp.sandbox.common.PatchRequestHelper
import com.ahold.ctp.sandbox.dto.BusinessSummaryDTO
import com.ahold.ctp.sandbox.dto.DeliveryDTO
import com.ahold.ctp.sandbox.dto.DeliveryUpdateDTO
import com.ahold.ctp.sandbox.exception.InvalidDeliveryRequestException
import com.ahold.ctp.sandbox.mapper.DeliveryMapper.Companion.mapToDelivery
import com.ahold.ctp.sandbox.mapper.DeliveryMapper.Companion.mapToDeliveryDTO
import com.ahold.ctp.sandbox.mapper.DeliveryMapper.Companion.mapToDeliveryDTOS
import com.ahold.ctp.sandbox.model.Delivery
import com.ahold.ctp.sandbox.repository.DeliveryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@Service
class DeliveryServiceImpl(
        @Autowired
        private val deliveryRepository: DeliveryRepository,
        @Autowired
        private val patchRequest : PatchRequestHelper) : IDeliveryService {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DeliveryServiceImpl::class.java)
    }

    override fun saveDelivery(deliveryDTO: DeliveryDTO): DeliveryDTO {
        logger.info("Saving delivery: $deliveryDTO")
        val delivery = mapToDelivery(deliveryDTO)
        return mapToDeliveryDTO(deliveryRepository.save(delivery))
    }

    override fun updateDelivery(deliveryUpdateDTO: DeliveryUpdateDTO): DeliveryDTO? {
        logger.info("Updating delivery: $deliveryUpdateDTO")
        var existingDelivery = getExistingDelivery(deliveryUpdateDTO)
        patchRequest.deliveryPatcher(existingDelivery, mapToDelivery(deliveryUpdateDTO))
        return mapToDeliveryDTO(deliveryRepository.save(existingDelivery))
    }

    override fun updateMultipleDeliveries(deliveryUpdateDTOS: List<DeliveryUpdateDTO>): List<DeliveryDTO> {
        logger.info("Updating multiple deliveries: $deliveryUpdateDTOS")
        var existingDeliveries = validateBulkUpdateDeliveriesAndGetExistingDeliveries(deliveryUpdateDTOS)
        var patchedDeliveries = patchBulkUpdateDeliveryList(existingDeliveries, deliveryUpdateDTOS)
        var persistedDeliveries = deliveryRepository.saveAll(patchedDeliveries)
        return mapToDeliveryDTOS(persistedDeliveries as List<Delivery>)
    }

    override fun getBusinessSummary(): BusinessSummaryDTO {
        return calculateDeliverySummary()
    }

    private fun getExistingDelivery(deliveryUpdateDTO: DeliveryUpdateDTO): Delivery{
        var existingDelivery:Optional<Delivery> = deliveryRepository.findById(UUID.fromString(deliveryUpdateDTO.id))
        if (existingDelivery.isEmpty ){
            throw InvalidDeliveryRequestException(
                    String.format("Delivery Record is not found for ID: %s", deliveryUpdateDTO.id))
        }
        return existingDelivery.get()
    }

    private fun validateBulkUpdateDeliveriesAndGetExistingDeliveries(
            deliveryUpdateDTOList:List<DeliveryUpdateDTO>):List<Delivery>{
        var ids = deliveryUpdateDTOList.map { deliveryUpdateDTO -> UUID.fromString(deliveryUpdateDTO.id) }.toList()
        var deliveryList = deliveryRepository.findAllById(ids)
        if(deliveryList.isEmpty()){
            throw InvalidDeliveryRequestException(
                    "No Delivery Records are available for the given bulk update input")
        }else if(deliveryUpdateDTOList.size != deliveryList.size){
            throw InvalidDeliveryRequestException(
                    "Few of the given input delivery records for bulk update are Invalid")
        }
        return deliveryList
    }

    private fun patchBulkUpdateDeliveryList(existingDeliveries:List<Delivery>,
                                            deliveryUpdateDTOList:List<DeliveryUpdateDTO>):List<Delivery?> {
        val deliveriesMap = mutableMapOf<UUID, Delivery>()
        existingDeliveries.associateByTo(deliveriesMap){it.id}
        var updatedDeliveryList = deliveryUpdateDTOList
                .map {
                    deliveryUpdateDTO ->updateDeliveryObject(deliveriesMap, deliveryUpdateDTO)
                }.toList()
        return updatedDeliveryList
    }

    private fun updateDeliveryObject(deliveriesMap:Map<UUID, Delivery>,
                                     deliveryUpdateDTO:DeliveryUpdateDTO):Delivery?{
        var existingDelivery = deliveriesMap.get(UUID.fromString(deliveryUpdateDTO.id))
        patchRequest.deliveryPatcher(existingDelivery, mapToDelivery(deliveryUpdateDTO))
        return existingDelivery
    }

    private fun calculateDeliverySummary(): BusinessSummaryDTO {
        val now = ZonedDateTime.now(ZoneId.of("Europe/Amsterdam"))
        val startOfDay = now.minusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0)
        val endOfDay = startOfDay.plusDays(1)

        logger.info("Calculating business summary for deliveries started between $startOfDay and $endOfDay")

        val deliveries = deliveryRepository.findDeliveriesStartedYesterday(startOfDay, endOfDay)

        val deliveryCount = deliveries.size

        val averageMinutesBetweenDeliveryStart =
                if (deliveryCount > 1) {
                    val totalMinutesBetweenDeliveries = deliveries.windowed(2, 1)
                            .sumOf { (first, second) -> ChronoUnit.MINUTES.between(first.startedAt, second.startedAt) }

                    (totalMinutesBetweenDeliveries / (deliveryCount - 1)).toInt()
                } else {
                    0
                }
        logger.info("Business summary calculated: $deliveryCount deliveries, " +
                "average minutes between delivery start: $averageMinutesBetweenDeliveryStart")
        return BusinessSummaryDTO(deliveryCount, averageMinutesBetweenDeliveryStart)
    }
}