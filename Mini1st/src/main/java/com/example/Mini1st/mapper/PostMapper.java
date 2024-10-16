package com.example.Mini1st.mapper;


import com.example.Mini1st.dao.CommentDTO;
import com.example.Mini1st.dao.PostDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface PostMapper {

    @Insert("INSERT INTO posts (user_id, contents, post_time, location, likes_count) VALUES (#{userId}, #{contents}, NOW(), #{location}, #{likesCount})")
        // 데이터베이스에 새로운 게시글을 삽입합니다.
    void insertPost(PostDTO postDTO);

    @Select("SELECT * FROM posts WHERE post_pk = #{postPk}")
        // 특정 ID를 가진 게시글을 조회합니다.
    PostDTO findPostById(int postPk);

    @Select("SELECT * FROM posts")
        // 모든 게시글을 조회하여 리스트로 반환합니다.
    List<PostDTO> findAllPosts();

    @Update("UPDATE posts SET contents = #{contents}, location = #{location}, likes_count = #{likesCount} WHERE post_pk = #{postPk}")
        // 특정 게시글의 내용을 업데이트합니다.
    void updatePost(PostDTO postDTO);

    @Delete("DELETE FROM posts WHERE post_pk = #{postPk}")
        // 특정 ID를 가진 게시글을 삭제합니다.
    void deletePost(int postPk);

    @Update("UPDATE posts SET likes_count = likes_count + 1 WHERE post_pk = #{postPk}")
        // 특정 게시글의 좋아요 수를 증가시킵니다.
    void incrementLikes(int postPk);

    @Insert("INSERT INTO comments (post_pk, user_id, content, create_at) VALUES (#{postPk}, #{userId}, #{content}, NOW())")
        // 특정 게시글에 댓글을 추가합니다.
    void addComment(CommentDTO commentDTO);
}

