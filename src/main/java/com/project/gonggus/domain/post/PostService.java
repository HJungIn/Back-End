package com.project.gonggus.domain.post;

import com.project.gonggus.domain.user.User;
import com.project.gonggus.domain.user.UserService;
import com.project.gonggus.domain.userpost.UserPost;
import com.project.gonggus.domain.userpost.UserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final UserService userService;
    private final UserPostService userPostService;
    private final PostRepository postRepository;

    public List<PostDto> getCategoryPosts(String category) {
        List<Post> postList = postRepository.findByCategory(category);
        return postList.stream().map(PostDto::convert).collect(Collectors.toList());
    }

    public List<PostDto> getSearchPosts(String searchTitle) {
        List<Post> postList = postRepository.findBySearchTitle(searchTitle);
        return postList.stream().map(PostDto::convert).collect(Collectors.toList());
    }

    public PostDto getPostDto(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        checkDateForFinishCheck(post);
        return post != null ? PostDto.convert(post) : null;
    }

    public void savePost(String userId, String title, String content, String category, String goodsLink, String limitNumberOfPeople, String deadline) {
        try {
            User user = userService.getUser(userId);

            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            Date deadline_date = fm.parse(deadline);
            Post post = new Post(user, title, content, category, goodsLink, Long.valueOf(1),Long.valueOf(limitNumberOfPeople), deadline_date, false);
            UserPost userPost = new UserPost(user, post);
            postRepository.save(post);
            userPostService.saveUserPost(userPost);
            user.getOwnPosts().add(post);
            user.getParticipatePosts().add(userPost);

        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void updatePost(Long postId, String title, String content, String category, String goodsLink, String limitNumberOfPeople, String deadline) {
        try {

            Post post = postRepository.findById(postId).get();

            post.setTitle(title);
            post.setContent(content);
            post.setCategory(category);
            post.setGoodsLink(goodsLink);
            post.setLimitNumberOfPeople(Long.valueOf(limitNumberOfPeople));
            if(!deadline.equals("")) {
                SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
                Date deadline_date = fm.parse(deadline);
                post.setDeadline(deadline_date);
            }

        } catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public Post getPost(Long postId){ return postRepository.findById(postId).orElse(null);}

    public void registerBookmark(String userId, Long postId) {
        User user = userService.getUser(userId);
        ArrayList<Long> list = user.getBookmarkPosts();
        if(list==null){
            list = new ArrayList<>();
        }
        if(list.contains(postId)) return;
        list.add(postId);
        user.setBookmarkPosts(list);
    }

    public void deleteBookmark(String userId, Long postId) {
        User user = userService.getUser(userId);
        user.getBookmarkPosts().remove(new Long(postId));
    }

    public void participatePost(String userId, Long postId) {
        User user = userService.getUser(userId);
        Post post = postRepository.findById(postId).orElse(null);
        if(user==null || post==null) return;

        UserPost userPost_check = userPostService.getUserPost(user, post);
        if(userPost_check!= null){
            return;
        }

        UserPost userPost = new UserPost(user, post);
        userPostService.saveUserPost(userPost);
        post.setCurrentNumberOfPeople(post.getCurrentNumberOfPeople()+1);
        if(post.getCurrentNumberOfPeople() == post.getLimitNumberOfPeople()){
            post.setFinishCheck(true);
        }
        user.getParticipatePosts().add(userPost);
    }

    public void withdrawPost(String userId, Long postId) {
        User user = userService.getUser(userId);
        Post post = postRepository.findById(postId).orElse(null);
        if(user==null || post==null) return;

        if(user == post.getOwner()){
            return;
        }

        UserPost userPost = userPostService.getUserPost(user, post);
        userPostService.deleteUserPost(userPost);
        post.setCurrentNumberOfPeople(post.getCurrentNumberOfPeople()-1);
        post.setFinishCheck(false);
        user.getParticipatePosts().remove(userPost);
    }

    public void deletePost(String userId, Long postId) {

        User user = userService.getUser(userId);
        Post post = getPost(postId);
        if(user == null || post==null)
            return;

        if(user == post.getOwner()){
            if(post.getCurrentNumberOfPeople()!=1)
                return;
            postRepository.delete(post);
            user.getOwnPosts().remove(post);
            user.getParticipatePosts().remove(userPostService.getUserPost(user, post));
            return;
        }

    }

    public void checkDateForFinishCheck(Post post){

        String current_str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date current = fm.parse(current_str);
            if( current.compareTo(post.getDeadline()) <= 0 ){

            }
            else{
                post.setFinishCheck(true);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}