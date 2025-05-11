package com.highway.lottery.modules.ticket.repo;
import com.highway.lottery.modules.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    @Query("SELECT t FROM Ticket  t WHERE  t.agent.id=:agentId ")
    List<Ticket> findAllByAgentId(@Param("agentId") Long agentId);
    Optional<Ticket> findTicketByTicketCode(String ticketCode);
}
