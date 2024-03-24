package com.ahold.ctp.sandbox.controller

import com.ahold.ctp.sandbox.dto.BulkUpdateDTO
import com.ahold.ctp.sandbox.dto.BusinessSummaryDTO
import com.ahold.ctp.sandbox.dto.DeliveryDTO
import com.ahold.ctp.sandbox.dto.DeliveryUpdateDTO
import com.ahold.ctp.sandbox.service.IDeliveryService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/deliveries")
@Validated
class DeliveryController(
        @Autowired
        private val deliveryService: IDeliveryService
) {

    @PostMapping
    fun createDelivery(@Valid @RequestBody delivery: DeliveryDTO): DeliveryDTO {
        return deliveryService.saveDelivery(delivery)
    }

    @PatchMapping("/{id}")
    fun updateDelivery(@PathVariable id: String, @Valid @RequestBody deliveryUpdateDTO: DeliveryUpdateDTO): DeliveryDTO? {
        deliveryUpdateDTO.id = id
        return deliveryService.updateDelivery(deliveryUpdateDTO)
    }

    @PatchMapping("/bulk-update")
    fun updateMultipleDeliveries(@RequestBody deliveries: List<@Valid DeliveryUpdateDTO>): BulkUpdateDTO {
        var deliveryDTOS = deliveryService.updateMultipleDeliveries(deliveries)
        return BulkUpdateDTO(deliveryDTOS)
    }

    @GetMapping("/business-summary")
    fun getBusinessSummary(): BusinessSummaryDTO {
        // Implement the logic to get the business summary
        return BusinessSummaryDTO(5, 10)
    }
}