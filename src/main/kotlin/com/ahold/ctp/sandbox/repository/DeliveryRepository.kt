package com.ahold.ctp.sandbox.repository

import com.ahold.ctp.sandbox.model.Delivery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import java.util.*

@Repository
interface DeliveryRepository : JpaRepository<Delivery, UUID> {
    @Query("SELECT d FROM Delivery d WHERE d.startedAt >= :startOfDay AND d.startedAt < :endOfDay ORDER BY d.startedAt ASC")
    fun findDeliveriesStartedYesterday(@Param("startOfDay") startOfDay: ZonedDateTime,
                                       @Param("endOfDay") endOfDay: ZonedDateTime): List<Delivery>
}