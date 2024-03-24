package com.ahold.ctp.sandbox.service

import com.ahold.ctp.sandbox.dto.BusinessSummaryDTO
import com.ahold.ctp.sandbox.dto.DeliveryDTO
import com.ahold.ctp.sandbox.dto.DeliveryUpdateDTO
import com.ahold.ctp.sandbox.model.Delivery
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

interface IDeliveryService {
    fun updateMultipleDeliveries(deliveries: List<DeliveryUpdateDTO>): List<DeliveryDTO>
    fun updateDelivery(deliveryUpdateDTO: DeliveryUpdateDTO): DeliveryDTO?
    fun saveDelivery(deliveryDTO: DeliveryDTO): DeliveryDTO
    fun getBusinessSummary(): BusinessSummaryDTO
}