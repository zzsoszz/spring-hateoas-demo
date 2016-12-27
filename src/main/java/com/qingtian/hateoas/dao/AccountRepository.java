package com.qingtian.hateoas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qingtian.hateoas.model.Account;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}