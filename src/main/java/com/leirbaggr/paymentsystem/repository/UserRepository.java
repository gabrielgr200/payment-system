package com.leirbaggr.paymentsystem.repository;

import com.leirbaggr.paymentsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);

    User findByVerificationCode(String verificationCode);
}
