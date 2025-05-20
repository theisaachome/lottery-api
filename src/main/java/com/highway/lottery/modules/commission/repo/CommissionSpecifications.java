package com.highway.lottery.modules.commission.repo;

import com.highway.lottery.modules.commission.entity.Commission;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class CommissionSpecifications {

    public static Specification<Commission> hasAgent(Long agentId) {
        return (root, query, cb) ->
                agentId == null ? null : cb.equal(root.get("agent").get("id"), agentId);
    }

    public static Specification<Commission> isWithdrawn(Boolean withdrawn) {
        return (root, query, cb) -> {
            if (withdrawn == null) return null;
            return withdrawn
                    ? cb.isNotNull(root.get("commissionWithdrawal"))
                    : cb.isNull(root.get("commissionWithdrawal"));
        };
    }

    public static Specification<Commission> earnedBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start != null && end != null) {
                return cb.between(root.get("earnedDate"), start, end);
            } else if (start != null) {
                return cb.greaterThanOrEqualTo(root.get("earnedDate"), start);
            } else if (end != null) {
                return cb.lessThanOrEqualTo(root.get("earnedDate"), end);
            }
            return null;
        };
    }
}
