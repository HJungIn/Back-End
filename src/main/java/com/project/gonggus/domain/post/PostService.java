package com.project.gonggus.domain.post;

import com.project.gonggus.domain.user.User;
import com.project.gonggus.domain.user.UserService;
import com.project.gonggus.domain.userpost.UserPost;
import com.project.gonggus.domain.userpost.UserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final UserService userService;
    private final UserPostService userPostService;
    private final PostRepository postRepository;

    public void savePost(Post post){
        postRepository.save(post);
    }

    public List<Post> getCategoryPosts(String category) {
        return postRepository.findByCategory(category);
    }

    public List<Post> getSearchPosts(String searchTitle) {
        return postRepository.findBySearchTitle(searchTitle);
    }

    public Optional<Post> getPost(Long postid) { return postRepository.findById(postid); }

    public void savePost(String userId, String title, String content, String category, String goodsLink, Long limitNumberOfPeople, String deadline) {
        try {
            User user = userService.getUser(userId);

            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            Date deadline_date = fm.parse(deadline);
            Post post = new Post(user, title, content, category, goodsLink, Long.valueOf(1),limitNumberOfPeople, deadline_date, false);
            postRepository.save(post);
            userPostService.saveUserPost(new UserPost(user, post));

        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void updatePost(Long postId, String title, String content, String category, String goodsLink, Long limitNumberOfPeople, String deadline) {
        try {

            Post post = postRepository.findById(postId).get();

            post.setTitle(title);
            post.setContent(content);
            post.setCategory(category);
            post.setGoodsLink(goodsLink);
            post.setLimitNumberOfPeople(limitNumberOfPeople);
            if(!deadline.equals("")) {
                SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
                Date deadline_date = fm.parse(deadline);
                post.setDeadline(deadline_date);
            }

        } catch (Exception e){
            System.out.println(e.toString());
        }

    }
}
