package com.highway.lottery.modules.ticket.repo;

import com.highway.lottery.modules.ticket.entity.TicketNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketNumberRepository extends JpaRepository<TicketNumber, Long> {
}
