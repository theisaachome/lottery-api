package com.highway.lottery.repository;
import com.highway.lottery.modules.commission.entity.CommissionWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface CommissionWithdrawalRepository extends JpaRepository<CommissionWithdrawal, Integer> {

    // 1. Get all withdrawals for a specific agent
//    List<CommissionWithdrawal> findByAgentId(String agentId);

    // 2. Get withdrawals by status (e.g., PENDING, COMPLETED)
//    List<CommissionWithdrawal> findByStatus(String status);

    // 3. Get all withdrawals within a specific time range (e.g., weekly report)
    @Query("""
        SELECT w FROM CommissionWithdrawal w
        WHERE w.createdAt BETWEEN :start AND :end
    """)
    List<CommissionWithdrawal> findByDateRange(@Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end);

    // 4. Get agent's withdrawal history within a specific period
    @Query("""
        SELECT w FROM CommissionWithdrawal w
        WHERE w.agent = :agentId AND w.createdAt BETWEEN :start AND :end
    """)
    List<CommissionWithdrawal> findByAgentAndDateRange(@Param("agentId") String agentId,
                                                       @Param("start") LocalDateTime start,
                                                       @Param("end") LocalDateTime end);


    // 5. Get the latest withdrawal record for an agent
//    CommissionWithdrawal findTopByAgentIdOrderByCreatedAtDesc(String agentId);
}
