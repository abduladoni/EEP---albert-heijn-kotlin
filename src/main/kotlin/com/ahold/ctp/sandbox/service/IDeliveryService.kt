package com.ahold.ctp.sandbox.service

import com.ahold.ctp.sandbox.dto.DeliveryDTO
import com.ahold.ctp.sandbox.model.Delivery
import java.util.*

interface IDeliveryService {
    fun getAllDeliveries(): List<Delivery>
    fun getDeliveryById(id: UUID): Delivery?
    fun saveDelivery(deliveryDTO: DeliveryDTO): DeliveryDTO
    fun deleteDelivery(id: UUID)
}