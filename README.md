# Back-End
<br/> 
<h4>개발 환경 : java, mysql</h4> 
<br/> 
<사용자> User<br/> 
id<br/> 
이름 name<br/> 
아이디 userId<br/> 
비번 userPassword<br/> 
닉네임 nickname<br/> 
학교이름 schoolName<br/> 
만든&참여 게시글 리스트 participatePosts<br/> 
만든 게시글 리스트 ownPosts<br/> 
북마크 리스트 bookmarkPosts<br/> 
댓글 리스트 myComments<br/>
<br/> 
<게시판> Post<br/> 
id<br/> 
제목 title<br/> 
방장 owner<br/> 
카테고리 category<br/> 
본문 content<br/> 
생성날짜 createdDate<br/> 
링크 goodsLink<br/> 
기한 deadline<br/> 
현재인원 currentNumberOfPeople<br/> 
제한인원 limitNumberOfPeople<br/> 
참여중인 사용자 participateUsers<br/> 
마감 여부 finishCheck<br/> 
댓글 comments <br/> 
<br/> 
<댓글> Comment<br/> 
id<br/> 
작성자 writer<br/> 
내용 content<br/> 
<br/> 
<사용자와 게시판> UserPost<br/> 
id<br/> 
user<br/> 
post<br/> 
<br/> 
<br/> 
<h4>역할분담</h4>
(로그인,)회원가입, 마이페이지, 프로필 수정하기<br/> 
메인 페이지, 카테고리별 게시글리스트, 게시글 검색, 게시글 작성하기, 게시글 수정하기, 상세 게시글( 참여하기 후 게시글(댓글나오도록 하기) ), 북마크 등록&삭제, 게시글 참여&탈퇴 <br>
북마크 게시글 보기, 참여한 게시글&본인이 만든 게시글 보기<br/> 
<br>
<br/> 


<hr>
<h3>path 경로 설정</h3>
<p>
메인 페이지 :  / <br/> 
로그인 페이지: /login <br/> 
회원가입 페이지: /register <br/>
로그아웃: /logout <br/>
로그인 여부 체크: /check <br/>
마이페이지: /mypage <br/>
마이페이지 회원정보 업데이트: /mypage/update <br/> 
마이페이지 북마크 리스트: /mypage/bookmark <br/>
마이페이지 참여 글 리스트: /mypage/participate <br/>
카테고리별 게시글리스트 :  /{category} <br/> 
게시글 검색 :  /searchpost?search=검색어 <br/> 
게시글 작성하기 :  /makepost  ,  완료후  :  /makepostsubmit <br/> 
게시글 수정하기 :  /post/{postId}/update  ,  완료후  :  /updatepostsubmit/{postId} <br/> 
상세 게시글 (참여하기 후 게시글(댓글나오도록 하기)) :  /post/{postId} <br/> 
</p>
<br/>
<br>
<h3>프론트와 백엔드 서로에게 필요한 데이터</h3>
<pre>
<br>
* 메인 페이지 : /<br>
<br>
* 카테고리별 게시글리스트 :  /{category} <br/> 
* 카테고리별 게시글리스트 : /{category} <br>
  -- 백엔드가 받는 데이터 : category (String) <br>
  -- 프론트에게 주는 데이터 : categoryPosts (List<Post>)<br>
<br>
* 게시글 검색 : /searchpost?search=검색어<br>
  -- 백엔드가 받는 데이터 : searchTitle (String)<br>
  -- 프론트에게 주는 데이터 : searchPosts (List<Post>)<br>
<br>
<br>
* 게시글 작성하기 : /makepost<br>
<br>
* 게시글 작성 완료후 : /makepostsubmit<br>
  -- 백엔드가 받는 데이터 :  @RequestParam("title") String title,<br>
                           @RequestParam("content") String content,<br>
                           @RequestParam("category") String category,<br>
                           @RequestParam("goodsLink") String goodsLink,<br>
                           @RequestParam("limitNumberOfPeople") Long limitNumberOfPeople,<br>
                           @RequestParam("deadline") String deadline<br>
<br><br>
* 게시글 수정하기 : /post/{postId}/update<br>
  -- 백엔드에게 받는 데이터 : postId (Long)<br>
  -- 프론트에게 주는 데이터 : post (Post)<br>
<br>
* 게시글 수정하기 완료후 : /updatepostsubmit/{postId}<br>
  -- 백엔드가 받는 데이터 :  @PathVariable("postId") Long postId<br>
                           @RequestParam("title") String title,<br>
                           @RequestParam("content") String content,<br>
                           @RequestParam("category") String category,<br>
                           @RequestParam("goodsLink") String goodsLink,<br>
                           @RequestParam("limitNumberOfPeople") Long limitNumberOfPeople,<br>
                           @RequestParam("deadline") String deadline<br>
<br>
<br>
* 상세 게시글 (참여하기 후 게시글(댓글나오도록 하기)) : /post/{postId}<br>
  -- 백엔드가 받는 데이터 : postId (Long)<br>
  -- 프론트에게 주는 데이터 : post (Post)<br>
  <br>
  <br>
  == 댓글 ==<br>
* 댓글 쓰기 : /post/{postId}/writecommentsubmit<br>
 - 백엔드가 받는 데이터 : postId (Long),<br>
		       content (String)<br>
<br>
* 댓글 수정하기 : /post/{postId}/updatecomment/{commentId}<br>
 - 백엔드가 받는 데이터 : postId (Long),<br>
		       commentId (Long)<br>
 - 프론트에게 주는 데이터 : comment (Comment)<br>
<br>
* 댓글 수정하기 완료후 : /post/{postId}/updatecommentsubmit/{commentId}<br>
 - 백엔드가 받는 데이터 : postId (Long),<br>
		       commentId (Long),<br>
		       content (String)<br>
* 댓글 삭제하기 : /post/{postId}/deletecomment/{commentId}<br>
 - 백엔드가 받는 데이터 : postId (Long),<br>
		       commentId (Long)<br>
<br>
===북마크 & 참여하기 +둘 다 삭제하기===<br>
* 북마크 등록하기 : /post/{postId}/registerbookmark<br>
 - 백엔드가 받는 데이터 : postId (Long)<br>
<br>
* 북마크 삭제하기 : /post/{postId}/deletebookmark<br>
 - 백엔드가 받는 데이터 : postId (Long)<br>
<br>
* 게시글에 참여하기 : /post/{postId}/participatepost<br>
 - 백엔드가 받는 데이터 : postId (Long)<br>
<br>
* 게시글에서 탈퇴하기 : /post/{postId}/withdrawpost<br>
 - 백엔드가 받는 데이터 : postId (Long)<br>
<br>
* 게시글 삭제하기 : /post/{postId}/deletepost<br>
 - 백엔드가 받는 데이터 : postId (Long)<br>
  <br>
  
</pre>
<br>
