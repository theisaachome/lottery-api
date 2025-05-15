package com.highway.lottery.modules.commission.repo;
import com.highway.lottery.modules.commission.entity.CommissionWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommissionWithdrawalRepo extends JpaRepository<CommissionWithdrawal, Long> {

   @Query("""
    SELECT w FROM CommissionWithdrawal w
    WHERE LOWER(w.agent.agentCode) LIKE LOWER(CONCAT('%',:agentCode,'%'))
""")
   List<CommissionWithdrawal> findByAgentCode(@Param("agentId")String code);
}
