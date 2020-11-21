package com.project.gonggus.domain.userpost;

import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPostRepository extends JpaRepository<UserPost, Long> {

    @Query("select up from UserPost up where up.user=:user and up.post=post")
    UserPost findByUserAndPost(@Param("user") User user, @Param("post") Post post);
}
