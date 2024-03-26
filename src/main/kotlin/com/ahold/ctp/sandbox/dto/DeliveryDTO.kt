package com.ahold.ctp.sandbox.dto

import com.ahold.ctp.sandbox.common.Status
import java.time.ZonedDateTime
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class DeliveryDTO(
    val id: String? = null,

    @field:NotBlank
    val vehicleId: String?,

    @field:NotNull
    val startedAt: ZonedDateTime?,

    val finishedAt: ZonedDateTime? = null,

    @field:NotNull
    val status: Status?
)