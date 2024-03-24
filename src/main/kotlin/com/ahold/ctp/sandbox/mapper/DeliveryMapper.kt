package com.ahold.ctp.sandbox.mapper

import com.ahold.ctp.sandbox.dto.DeliveryDTO
import com.ahold.ctp.sandbox.dto.DeliveryUpdateDTO
import com.ahold.ctp.sandbox.model.Delivery
import java.time.ZoneId
import java.util.*

class DeliveryMapper {
    companion object {
        // mapper method to convert DeliveryDTO to Delivery
        fun mapToDelivery(deliveryDTO: DeliveryDTO): Delivery {
            return Delivery(
                    id = deliveryDTO.id?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
                    startedAt = deliveryDTO.startedAt,
                    vehicleId = deliveryDTO.vehicleId,
                    status = deliveryDTO.status,
                    finishedAt = deliveryDTO.finishedAt
            )
        }

        // mapper method to convert Delivery to DeliveryDTO
        fun mapToDeliveryDTO(delivery: Delivery): DeliveryDTO {
            return DeliveryDTO(
                    id = delivery.id.toString(),
                    startedAt = delivery.startedAt?.withZoneSameInstant(ZoneId.of("UTC")),
                    vehicleId = delivery.vehicleId,
                    status = delivery.status,
                    finishedAt = delivery.finishedAt?.withZoneSameInstant(ZoneId.of("UTC"))
            )
        }

        fun mapToDelivery(deliveryUpdateDTO : DeliveryUpdateDTO):Delivery{
            return Delivery(
                    id = UUID.fromString(deliveryUpdateDTO.id),
                    startedAt = null,
                    finishedAt = deliveryUpdateDTO.finishedAt,
                    status = deliveryUpdateDTO.status,
                    vehicleId = null

            )

        }
        fun mapToDeliveryDTOS(deliveries: List<Delivery>): List<DeliveryDTO>{

            return deliveries.map { delivery: Delivery -> DeliveryDTO(
                    id = delivery.id.toString(),
                    startedAt = delivery.startedAt?.withZoneSameInstant(ZoneId.of("UTC")),
                    vehicleId = delivery.vehicleId,
                    status = delivery.status,
                    finishedAt = delivery.finishedAt?.withZoneSameInstant(ZoneId.of("UTC"))
            ) }.toList()

        }
    }
}