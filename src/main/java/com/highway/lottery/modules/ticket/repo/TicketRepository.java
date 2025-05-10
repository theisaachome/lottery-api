package com.highway.lottery.modules.ticket.repo;
import com.highway.lottery.modules.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}
