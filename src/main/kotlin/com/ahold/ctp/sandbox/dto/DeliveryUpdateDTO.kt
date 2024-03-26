package com.ahold.ctp.sandbox.dto

import com.ahold.ctp.sandbox.common.Status
import java.time.ZonedDateTime
import jakarta.validation.constraints.NotNull

data class DeliveryUpdateDTO(
    @field:NotNull
    var finishedAt: ZonedDateTime?,

    @field:NotNull
    var status: Status?,
    var id: String? = null,
)