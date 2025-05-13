package com.highway.lottery.modules.account.repo;
import com.highway.lottery.modules.account.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.username = :username AND a.phone = :phone")
    Optional<Account> findAccountByUsernameAndPhone(@Param("username") String username,
                                                    @Param("phone") String phone);

    @EntityGraph(attributePaths = {"roles"})
    Optional<Account> findAccountByUsername(String username);

    @Query("SELECT a FROM Account a LEFT JOIN a.roles WHERE a.username=:username")
    Optional<Account> findAccountWithRolesByUsername(@Param("username")String username);


    @Query("SELECT a FROM Account  a WHERE a.active=true ")
    List<Account> findAccountByActive();
    boolean  existsAccountByPhone(String phone);
    boolean existsAccountByUsername(String username);
}
