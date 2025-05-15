package com.highway.lottery.modules.commission.repo;
import com.highway.lottery.modules.commission.dto.CommissionResponse;
import com.highway.lottery.modules.commission.entity.Commission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CommissionRepository extends JpaRepository<Commission, Integer> {
    // Get all commissions for an agent by agent-code (withdraw status false)
    @Query("""
        SELECT c FROM  Commission c 
        WHERE LOWER(c.agent.agentCode) LIKE LOWER(CONCAT('%',:agentCode,'%')) AND c.commissionWithdrawal IS NULL 
""")
    List<Commission> findByAgentCode(@Param("agentId")String  agentCode);

    @Query("""
        SELECT new com.highway.lottery.modules.commission.dto.CommissionResponse(
          c.id,
          c.agent.id,
          c.ticket.id,
          c.amount,
          c.earnedDate,
          c.commissionWithdrawal.id
        )
        FROM Commission c
        WHERE c.agent.id = :agentId
    """)
    Page<CommissionResponse> findByAgentId(@Param("agentId") Long agentId, Pageable pageable);


    // Get all-time commissions (optional filter by agent)
    @Query("SELECT c FROM Commission c WHERE (:agentId IS NULL OR c.agent = :agentId)")
    List<Commission> findAllCommissions(@Param("agentId") String agentId);

    // Get all active commissions (admin operation)
    @Query("SELECT c FROM Commission c WHERE c.active = true ")
    List<Commission> findAllActiveCommissions();

    // Get commission history within a period for a specific agent
    @Query("""
           SELECT c FROM Commission c 
           WHERE c.agent = :agentId 
             AND c.earnedDate BETWEEN :from AND :to
           """)
    List<Commission> findCommissionByAgentAndDateRange(
            @Param("agentId") Long agentId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );


}
