package com.ahold.ctp.sandbox.controller

import com.ahold.ctp.sandbox.dto.BulkUpdateDTO
import com.ahold.ctp.sandbox.dto.BusinessSummaryDTO
import com.ahold.ctp.sandbox.dto.DeliveryDTO
import com.ahold.ctp.sandbox.dto.DeliveryUpdateDTO
import com.ahold.ctp.sandbox.exception.ApiErrorResponse
import com.ahold.ctp.sandbox.service.IDeliveryService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema

@RestController
@RequestMapping("/deliveries")
@Validated
class DeliveryController(
        @Autowired
        private val deliveryService: IDeliveryService
) {

    @Operation(summary = "Create a new delivery")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully created delivery",
            content = [Content(schema = Schema(implementation = DeliveryDTO::class))]),
        ApiResponse(responseCode = "400", description = "Invalid request",
            content = [Content(schema = Schema(implementation = ApiErrorResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error",
            content = [Content(schema = Schema(implementation = ApiErrorResponse::class))])
    ])
    @PostMapping
    fun createDelivery(@Valid @RequestBody delivery: DeliveryDTO): DeliveryDTO {
        return deliveryService.saveDelivery(delivery)
    }

    @Operation(summary = "Update an existing delivery")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully updated delivery",
            content = [Content(schema = Schema(implementation = DeliveryDTO::class))]),
        ApiResponse(responseCode = "400", description = "Invalid request",
            content = [Content(schema = Schema(implementation = ApiErrorResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error",
            content = [Content(schema = Schema(implementation = ApiErrorResponse::class))])
    ])
    @PatchMapping("/{id}")
    fun updateDelivery(@PathVariable id: String, @Valid @RequestBody deliveryUpdateDTO: DeliveryUpdateDTO): DeliveryDTO? {
        deliveryUpdateDTO.id = id
        return deliveryService.updateDelivery(deliveryUpdateDTO)
    }

    @Operation(summary = "Update multiple deliveries")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully updated multiple deliveries",
            content = [Content(schema = Schema(implementation = BulkUpdateDTO::class))]),
        ApiResponse(responseCode = "400", description = "Invalid request",
            content = [Content(schema = Schema(implementation = ApiErrorResponse::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error",
            content = [Content(schema = Schema(implementation = ApiErrorResponse::class))])
    ])
    @PatchMapping("/bulk-update")
    fun updateMultipleDeliveries(@RequestBody deliveries: List<@Valid DeliveryUpdateDTO>): BulkUpdateDTO {
        var deliveryDTOS = deliveryService.updateMultipleDeliveries(deliveries)
        return BulkUpdateDTO(deliveryDTOS)
    }

    @Operation(summary = "Get business summary")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved business summary",
            content = [Content(schema = Schema(implementation = BusinessSummaryDTO::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error",
            content = [Content(schema = Schema(implementation = ApiErrorResponse::class))])
    ])
    @GetMapping("/business-summary")
    fun getBusinessSummary(): BusinessSummaryDTO {
        return deliveryService.getBusinessSummary()
    }
}