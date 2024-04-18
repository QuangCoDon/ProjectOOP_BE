package com.project.project_oop.repository;

import com.project.project_oop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(
            value = "select u from User u inner join EmailVerificationToken e_t on u.id = e_t.user.id where e_t.token = :token and (e_t.expired = 0)"
    )
    Optional<User> findByEmailVerificationToken(String token);

}
