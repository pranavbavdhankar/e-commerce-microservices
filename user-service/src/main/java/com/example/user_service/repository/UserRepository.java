package com.example.user_service.repository;

import com.example.user_service.model.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserPrincipal, String> {
    boolean existsByEmail(String email);

    UserPrincipal getByEmail(String email);
}
