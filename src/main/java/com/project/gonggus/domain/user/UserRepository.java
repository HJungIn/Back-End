package com.project.gonggus.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.userId like :userId")
    User findByUserId(@Param ("userId") String userId);

    @Query("select u from User u where u.id = :userIdx")
    User findByUserIdx(@Param ("userIdx") Long userIdx);
}
