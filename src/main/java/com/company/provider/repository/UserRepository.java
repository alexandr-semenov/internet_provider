package com.company.provider.repository;

import com.company.provider.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query()
    User findByUsername(String username);

    Optional<User> findById(Long id);

    Page<User> findByActive(Pageable paging, boolean active);
}
