package com.example.Mini1st.dao;

public class CommentDTO {
// CommentDTO는 댓글 관련 데이터를 전송하기 위한 객체로,
// 댓글 작성 요청을 처리할 때 사용됩니다.

    //생성자
    private int commentPk ; //댓글 식별자
    private int postPk; // 댓글이 속한 게시글ID
    private String userId; // 댓글작성자
    private String content; //댓글 내용
    private String createAt ; // 댓글 작성 시간


    // 기본 생성자
    public CommentDTO(){}


    // 모든 필드를 포함한 생성자
    public CommentDTO(int commentPk, int postPk, String userId, String content, String createAt) {
        this.commentPk = commentPk;
        this.postPk = postPk;
        this.userId = userId;
        this.content = content;
        this.createAt = createAt;
    }

    // Getter와 Setter
    public int getCommentPk() {
        return commentPk;
    }

    public void setCommentPk(int commentPk) {
        this.commentPk = commentPk;
    }

    public int getPostPk() {
        return postPk;
    }

    public void setPostPk(int postPk) {
        this.postPk = postPk;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
