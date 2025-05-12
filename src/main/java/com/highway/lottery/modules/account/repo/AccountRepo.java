package com.highway.lottery.modules.account.repo;
import com.highway.lottery.modules.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.username = :username AND a.phone = :phone")
    Optional<Account> findAccountByUsernameAndPhone(@Param("username") String username,
                                                    @Param("phone") String phone);

    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountByPhone(String phone);
    @Query("SELECT a FROM Account  a WHERE a.active=true ")
    List<Account> findAccountByActive();
    boolean  existsAccountByPhone(String phone);
    boolean existsAccountByUsername(String username);
}
