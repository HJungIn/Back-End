package com.project.gonggus.domain.userpost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPostService {
    private final UserPostRepository userPostRepository;
    public void saveUserPost(UserPost userPost){userPostRepository.save(userPost);}
}
