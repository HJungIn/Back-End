package com.project.gonggus.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public void savePost(Post post){postRepository.save(post);}

    public List<Post> getCategoryPosts(String category) {
        return postRepository.findByCategory(category);
    }

    public List<Post> getSearchPosts(String searchTitle) {
        return postRepository.findBySearchTitle(searchTitle);
    }
}
