package com.highway.lottery.repository;

import com.highway.lottery.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.username = :username AND a.phone = :phone")
    Optional<Account> findAccountByUsernameAndPhone(@Param("username") String username,
                                                    @Param("phone") String phone);
    boolean  existsAccountByPhone(String phone);
    Optional<Account> findAccountByPhone(String phone);

}
