package com.ahold.ctp.sandbox.mapper

import com.ahold.ctp.sandbox.dto.DeliveryDTO
import com.ahold.ctp.sandbox.model.Delivery
import java.util.*

class DeliveryMapper {
    companion object {
        // mapper method to convert DeliveryDTO to Delivery
        fun mapToDelivery(deliveryDTO: DeliveryDTO): Delivery {
            return Delivery(
                    id = deliveryDTO.id?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
                    startedAt = deliveryDTO.startedAt,
                    vehicleId = deliveryDTO.vehicleId,
                    status = deliveryDTO.status
            )
        }

        // mapper method to convert Delivery to DeliveryDTO
        fun mapToDeliveryDTO(delivery: Delivery): DeliveryDTO {
            return DeliveryDTO(
                    id = delivery.id.toString(),
                    startedAt = delivery.startedAt,
                    vehicleId = delivery.vehicleId,
                    status = delivery.status
            )
        }
    }
}