package com.highway.lottery.repository;
import com.highway.lottery.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}
