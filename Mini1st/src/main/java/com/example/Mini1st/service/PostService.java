package com.example.Mini1st.service;

import com.example.Mini1st.dao.CommentDTO;
import com.example.Mini1st.dao.PostDTO;
import com.example.Mini1st.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostMapper postMapper;

    @Autowired
    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }
     // PostMapper 인터페이스를 자동 주입받아 사용합니다.
    // 생성자 주입 방식으로 의존성을 주입합니다.

    // 게시글 생성
    public PostDTO createPost(String content_box) {
        // PostMapper를 사용해 데이터베이스에 게시글을 추가합니다.
        PostDTO postDTO = new PostDTO();
        /* 더미 1234 유저 생성 */
        postDTO.setUserId("1234");
        postDTO.setContents(content_box);

        try {
            postMapper.insertPost(postDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return postDTO;
    }

    // 게시글 조회 (단일 조회)
    public PostDTO getPost(int postPk) {
        // 특정 게시글 ID(postPk)를 사용해 데이터베이스에서 해당 게시글을 조회합니다.
        return postMapper.findPostById(postPk);
    }

    // 게시글 목록 조회 (전체 조회)
    public List<PostDTO> getAllPosts() {
        // 데이터베이스에서 모든 게시글을 조회하여 리스트로 반환합니다.
        return postMapper.findAllPosts();
    }

    // 게시글 업데이트
    public PostDTO updatePost(int postPk, PostDTO postDTO) {
        // 업데이트할 게시글의 ID를 설정한 후, 해당 게시글 정보를 데이터베이스에 업데이트합니다.
        postMapper.updatePost(postDTO);
        return postDTO;
    }

    // 게시글 삭제
    public void deletePost(int postPk) {
        // 특정 게시글 ID(postPk)를 사용해 해당 게시글을 데이터베이스에서 삭제합니다.
        postMapper.deletePost(postPk);
    }

    // 게시글 좋아요 누적
    public void likePost(int postPk) {
        // 특정 게시글의 좋아요 수를 증가시킵니다.
        postMapper.incrementLikes(postPk);
    }

    // 댓글 작성
    public CommentDTO addComment(int postPk, CommentDTO commentDTO) {
        // 특정 게시글에 댓글을 추가합니다.
        commentDTO.setPostPk(postPk);
        postMapper.addComment(commentDTO);
        return commentDTO;
    }
}