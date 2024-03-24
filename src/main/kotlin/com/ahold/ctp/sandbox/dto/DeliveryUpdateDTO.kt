package com.ahold.ctp.sandbox.dto

import com.ahold.ctp.sandbox.common.Status
import java.time.ZonedDateTime
import jakarta.validation.constraints.NotBlank

data class DeliveryUpdateDTO(
    @field:NotBlank
    var finishedAt: ZonedDateTime?,

    @field:NotBlank
    var status: Status?,
    var id: String? = null,
)