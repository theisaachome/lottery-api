package com.highway.lottery.modules.commission.repo;
import com.highway.lottery.modules.commission.dto.CommissionSummaryDTO;
import com.highway.lottery.modules.commission.dto.CommissionWithTicketDTO;
import com.highway.lottery.modules.commission.entity.Commission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommissionRepository extends JpaRepository<Commission, Integer>, JpaSpecificationExecutor<Commission> {

    @Override
    @EntityGraph(attributePaths = {"agent", "commissionWithdrawal"})
    Page<Commission>  findAll(Specification<Commission> spec, Pageable pageable);

    // Get all commissions for an agent by agent-code (withdraw status false)
    @Query("""
        SELECT c FROM  Commission c 
        WHERE LOWER(c.agent.agentCode) LIKE LOWER(CONCAT('%',:agentCode,'%')) AND c.commissionWithdrawal IS NULL 
""")
    List<Commission> findByAgentCode(@Param("agentId")String  agentCode);

    @Query("""
        SELECT new com.highway.lottery.modules.commission.dto.CommissionSummaryDTO(
          c.id,
          c.agent.id,
          c.ticket.ticketCode,
          c.amount,
          c.earnedDate,
          c.commissionWithdrawal.id
        )
        FROM Commission c
        WHERE c.agent.id = :agentId AND c.commissionWithdrawal is NULL AND c.active IS TRUE
    """)
    Page<CommissionSummaryDTO> findAllCommissionAvailableByAgentId(@Param("agentId") Long agentId, Pageable pageable);


    // Get all-time commissions (optional filter by agent)
    @Query("SELECT c FROM Commission c WHERE (:agentId IS NULL OR c.agent = :agentId)")
    List<Commission> findAllCommissions(@Param("agentId") String agentId);

    // Get all active commissions (admin operation)
    @Query("SELECT c FROM Commission c WHERE c.active = true ")
    List<Commission> findAllActiveCommissions();

    // Get commission history within a period for a specific agent
    @Query("""
           SELECT c FROM Commission c 
           WHERE c.agent.id = :agentId 
             AND c.earnedDate BETWEEN :from AND :to AND c.commissionWithdrawal IS NULL
           """)
    List<Commission> findCommissionByAgentAndDateRange(
            @Param("agentId") Long agentId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );


    @Query("""
        SELECT new com.highway.lottery.modules.commission.dto.CommissionWithTicketDTO(
            c.id,
            c.amount,
            c.earnedDate,
            (c.commissionWithdrawal IS NOT NULL),
            new com.highway.lottery.modules.commission.dto.TicketSummaryDTO(
                t.id,
                t.ticketCode,
                t.totalAmount,
                t.drawDate,
                t.drawType
            )
        )
        FROM Commission c
        JOIN c.ticket t
        WHERE c.id = :commissionId
    """)
    Optional<CommissionWithTicketDTO> findCommissionWithTicketByCommissionId(@Param("commissionId") Long commissionId);

}
