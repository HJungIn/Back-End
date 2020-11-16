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
(로그인,)회원가입, 마이페이지<br/> 
메인 페이지, 카테고리별 게시글리스트, 게시글 검색, 게시글 작성하기, 수정하기, 상세 게시글 <br/> 
참여하기 후 게시글(댓글나오도록 하기) <br/> 
<br/> 

<hr>
<h3>path 경로 설정</h3>
<p>
메인 페이지 :  / <br/> 
카테고리별 게시글리스트 :  /{category} <br/> 
게시글 검색 :  /searchpost?search=검색어 <br/> 
게시글 작성하기 :  /makepost  ,  완료후  :  /makepostsubmit <br/> 
게시글 수정하기 :  /post/{postId}/update  ,  완료후  :  /updatepostsubmit/{postId} <br/> 
상세 게시글 (참여하기 후 게시글(댓글나오도록 하기)) :  /post/{postId} <br/> 
</p>
<br/>
