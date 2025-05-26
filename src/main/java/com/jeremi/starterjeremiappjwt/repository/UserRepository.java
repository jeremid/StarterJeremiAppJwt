package com.jeremi.starterjeremiappjwt.repository;

import com.jeremi.starterjeremiappjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

}
