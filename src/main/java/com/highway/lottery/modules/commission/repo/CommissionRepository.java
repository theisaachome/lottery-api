package com.highway.lottery.modules.commission.repo;
import com.highway.lottery.domain.entity.Commission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CommissionRepository extends JpaRepository<Commission, Integer> {
    // Get all commissions for an agent by agent ID (withdraw status false)
    @Query("SELECT c FROM  Commission c WHERE c.agent=:agentId AND c.withdrawn=false")
    List<Commission> findByAgent(@Param("agentId")String agentId);

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
             AND c.createdAt BETWEEN :startDate AND :endDate
           """)
    List<Commission> findCommissionByAgentAndDateRange(
            @Param("agentId") String agentId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


}
