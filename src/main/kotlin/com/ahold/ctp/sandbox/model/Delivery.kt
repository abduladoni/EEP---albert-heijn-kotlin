package com.ahold.ctp.sandbox.model

import com.ahold.ctp.sandbox.common.Status
import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "deliveries")
@Setter
@Getter
class Delivery(
    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: UUID = UUID.randomUUID(),

    @Column(name = "vehicle_id")
    var vehicleId: String?,

    @Column(name = "started_at")
    var startedAt: ZonedDateTime?,

    @Column(name = "finished_at")
    var finishedAt: ZonedDateTime?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: Status?,
)