package com.ahold.ctp.sandbox.dto

import com.ahold.ctp.sandbox.common.Status
import java.time.ZonedDateTime
import jakarta.validation.constraints.NotBlank

data class DeliveryUpdateDTO(
    @field:NotBlank
    val finishedAt: ZonedDateTime,

    @field:NotBlank
    val status: Status,
    val id: String? = null,
)