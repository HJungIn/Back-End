package com.project.gonggus.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByUserId(@Param("user_id") String userId);
}
