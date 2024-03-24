package com.ahold.ctp.sandbox.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

data class BulkUpdateDTO(
    @field:Valid
    @field:NotEmpty
    val deliveries: List<DeliveryUpdateDTO>
)