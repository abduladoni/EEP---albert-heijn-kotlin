package com.ahold.ctp.sandbox.common

import com.ahold.ctp.sandbox.model.Delivery
import org.springframework.stereotype.Component

@Component
object PatchRequestHelper {
    @Throws(IllegalAccessException::class)
    fun deliveryPatcher(existingDelivery: Delivery?, partialDelivery: Delivery?) {
        val deliveryClass: Class<*> = Delivery::class.java
        val declaredFields = deliveryClass.declaredFields
        for (field in declaredFields) {
            field.isAccessible = true
            val value = field[partialDelivery]
            if (value != null) {
                field[existingDelivery] = value
            }
            field.isAccessible = false
        }
    }
}