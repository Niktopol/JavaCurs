package com.coffehouse.app.repository;

import com.coffehouse.app.model.User;
import com.coffehouse.app.model.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByName(String name);
    Optional<User> findByUsername(String username);
    List<User> findAllByRole(Role role);

    List<User> findAllByRoleAndName(Role role, String name);
    Optional<User> findByRoleAndUsername(Role role, String username);
    Optional<User> findByRoleAndId(Role role, Long id);

    boolean existsByUsername(String username);
}
