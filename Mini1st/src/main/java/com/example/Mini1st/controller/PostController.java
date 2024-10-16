package com.example.Mini1st.controller;

// 컨트롤러와 서비스 계층의 역할분리:
// 컨트롤러의 역할을 단순화 하고 ,비즈니스 로직은 서비스 계층에서
// 처리하도록하여 각 계층의 책임을 명확히 한다

// 컨트롤러 : http 요청을 처리, 서비스계층으로 요청 전달
// 단순한 처리를 하는 중간역할

// 서비스 계층 : 비즈니스 로직 처리, DAO호출해 필요한 DB작업수행
// 비즈니스로직 이란? 여러 작업을 수행할때 필요한 규칙,
// 데이터의 조합, 특정 조건에 따른 처리를 포함하는 로직을 정의

// 예를 들어 좋아요 누적기능 역할분리
// 컨트롤러 : 사용자 요청 수신 , 서비스 계층에 요청전달
// 서비스 계층 : 로직수행, 게시글 여부확인, 좋아요 수 증가
// 서비스는 데이터의 검증, 예외처리, 설정값추가 등 요청에 따른 모든작업

import com.example.Mini1st.dao.CommentDTO;
import com.example.Mini1st.dao.PostDTO;
import com.example.Mini1st.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/posts")
public class PostController {

    //왜 @Autowired를 사용하는가?
    // 1. 필요한 객체 자동으로 주입받을수 있음
    // 스프링 컨테이너는 해당 타입의 Bean을 찾아서 클래스에 주입
    // 코드 재사용이 쉬워진다
    // 2. 코드가 간결해진다
    // 객체생성에 대한 로직이 줄기때문에 유지보수성 올라감
    // 3. 클래스 간의 결합도 감소로 서로 독립적 유지
    // 객체를 직접생성하는 대신 스프링 컨테이너가 주입해주기때문에
    // 테스트와 변경이 용이하다
    // 단위 테스트 작성시 의존성을 Mock object로 대체하여 테스트
    @Autowired
    private  PostService postService;

    //게시글 생성
    // 새로운 게시글을 생성하기 위해 PostDTO를 받아 서비스 계층에 전달
    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        PostController PostService = new PostController();
        return postService.createPost(postDTO);
    }
    // 게시글 조회 (단일 조회)
    @GetMapping("/{postPk}")
    public PostDTO getPost(@PathVariable int postPk) {
        // 특정 ID에 해당하는 게시글을 조회하고 PostVO로 반환
        return postService.getPost(postPk);
    }

    // 게시글 목록 조회 (전체 조회)
    @GetMapping
    public List<PostDTO> getAllPosts() {
        // 모든 게시글을 조회하여 불변 객체인 List<PostVO>로 반환
        return postService.getAllPosts();
    }

    // 게시글 업데이트
    @PutMapping("/{postPk}")
    public PostDTO updatePost(@PathVariable int postPk, @RequestBody PostDTO postDTO) {
        // 특정 게시글을 업데이트하기 위해 PostDTO와 ID를 받아 서비스 계층에 전달
        return postService.updatePost(postPk, postDTO);
    }

    // 게시글 삭제
    @DeleteMapping("/{postPk}")
    public String deletePost(@PathVariable int postPk) {
        // 특정 게시글 삭제 요청을 서비스 계층에 전달
        postService.deletePost(postPk);
        return "게시글이 삭제되었습니다.";
    }

    // 게시글 좋아요 누적
    @PostMapping("/{postPk}/like")
    public String likePost(@PathVariable int postPk) {
        // 특정 게시글의 좋아요 수 증가 요청을 서비스 계층에 전달
        postService.likePost(postPk);
        return "좋아요가 추가되었습니다.";
    }

    // 댓글 작성
    @PostMapping("/{postPk}/comments")
    public CommentDTO addComment(@PathVariable int postPk, @RequestBody CommentDTO commentDTO) {
        return postService.addComment(postPk, commentDTO);
    }
}
