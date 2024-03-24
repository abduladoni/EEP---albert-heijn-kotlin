package com.ahold.ctp.sandbox.exception

import java.time.LocalDateTime

data class ApiErrorResponse(
        val message: String? = null,
        val status: Int = 0,
        val errors: MutableList<String?>,
        val timestamp: LocalDateTime? = null,
)