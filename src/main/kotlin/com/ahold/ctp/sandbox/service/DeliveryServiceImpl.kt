package com.ahold.ctp.sandbox.service

import com.ahold.ctp.sandbox.dto.DeliveryDTO
import com.ahold.ctp.sandbox.mapper.DeliveryMapper.Companion.mapToDelivery
import com.ahold.ctp.sandbox.mapper.DeliveryMapper.Companion.mapToDeliveryDTO
import com.ahold.ctp.sandbox.model.Delivery
import com.ahold.ctp.sandbox.repository.DeliveryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DeliveryServiceImpl(
        @Autowired
        private val deliveryRepository: DeliveryRepository) : IDeliveryService {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DeliveryServiceImpl::class.java)
    }

    override fun getAllDeliveries(): List<Delivery> {
        logger.info("Getting all deliveries")
        return deliveryRepository.findAll()
    }

    override fun getDeliveryById(id: UUID): Delivery? {
        logger.info("Getting delivery by id: $id")
        return deliveryRepository.findById(id).orElse(null)
    }

    override fun saveDelivery(deliveryDTO: DeliveryDTO): DeliveryDTO {
        logger.info("Saving delivery: $deliveryDTO")
        val delivery = mapToDelivery(deliveryDTO)
        return mapToDeliveryDTO(deliveryRepository.save(delivery))
    }

    override fun deleteDelivery(id: UUID) {
        logger.info("Deleting delivery with id: $id")
        deliveryRepository.deleteById(id)
    }
}