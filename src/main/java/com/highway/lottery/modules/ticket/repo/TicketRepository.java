package com.highway.lottery.modules.ticket.repo;
import com.highway.lottery.modules.ticket.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID>, JpaSpecificationExecutor<Ticket> {

    @EntityGraph(attributePaths = "ticketNumbers")
    @Query("SELECT t FROM Ticket  t WHERE  t.agent.id=:agentId ")
    Page<Ticket> findSoldAllTicketsByAgentId(@Param("agentId") Long agentId, Pageable pageable);

    Optional<Ticket> findTicketByTicketCode(String ticketCode);

    @Query("""
          SELECT t FROM Ticket t
          JOIN FETCH t.agent a
          LEFT JOIN FETCH t.ticketNumbers tn
          WHERE t.ticketCode = :ticketCode AND a.username = :username
    """)
    Optional<Ticket> findByTicketCodeAndAgentCodeWithDetails(@Param("ticketCode")String ticketCode, @Param("username")String username);


    //Filter by Draw Date or Date Range

     //Example: findAllByDrawDate(LocalDate drawDate)

    //Example: findAllByDrawDateBetween(LocalDate start, LocalDate end)

    //Useful for filtering tickets per draw session.

//    Search by Customer Name or Phone (partial match)

//    Example:

//    findAllByCustomerNameContainingIgnoreCase(String keyword)

//    findAllByPhoneContaining(String phonePart)

//    Helps admins track down tickets when customers call.

//    🔍 6. Combination Filtering
//
//    Find tickets by agent and draw date:
//
//    findAllByAgentCodeAndDrawDate(String agentCode, LocalDate drawDate)
//
//    Find tickets by agent, draw type and date range:
//
//    findAllByAgentCodeAndDrawTypeAndDrawDateBetween(...)

//    . Summaries & Reporting
//
//    Count or sum by date or agent:
//
//    long countByDrawDate(LocalDate date)
//
//    BigDecimal sumTotalAmountByDrawDate(LocalDate date)
//
//    Use custom @Query or native queries for these.
}
