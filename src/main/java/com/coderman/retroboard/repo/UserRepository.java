package com.coderman.retroboard.repo;

import com.coderman.retroboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    // @Query annotation is not required as the naming of the method will be used to create the filter.
    User findByUsername(String username);
}
