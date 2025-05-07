package com.highway.lottery.repository;

import com.highway.lottery.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Integer> {
}
