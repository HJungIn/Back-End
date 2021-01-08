package com.project.gonggus.domain.userpost;

import com.project.gonggus.domain.post.Post;
import com.project.gonggus.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPostService {
    private final UserPostRepository userPostRepository;
    public void saveUserPost(UserPost userPost){userPostRepository.save(userPost);}

    public UserPost getUserPost(User user, Post post) {
        return userPostRepository.findByUserAndPost(user, post);
    }

    public void deleteUserPost(UserPost userPost) {
        userPostRepository.delete(userPost);
    }
}
