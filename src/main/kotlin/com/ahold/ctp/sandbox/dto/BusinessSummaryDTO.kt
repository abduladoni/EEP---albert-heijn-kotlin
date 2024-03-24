package com.ahold.ctp.sandbox.dto

import jakarta.validation.constraints.Min

data class BusinessSummaryDTO(
    @field:Min(0)
    val deliveries: Int,

    @field:Min(0)
    val averageMinutesBetweenDeliveryStart: Int
)