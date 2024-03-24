package com.ahold.ctp.sandbox.repository

import com.ahold.ctp.sandbox.common.Status
import com.ahold.ctp.sandbox.model.Delivery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface DeliveryRepository : JpaRepository<Delivery, UUID> {
}