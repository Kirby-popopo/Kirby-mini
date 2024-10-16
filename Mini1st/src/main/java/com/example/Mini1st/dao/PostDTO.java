package com.example.Mini1st.dao;


// DTO : (목적)데이터 전송 // 데이터계층 <--> 뷰
// 계층간 정보를 안전하게 주고 받기 위해 사용
// DB 데이터 조회 후 UI전달하거나
// 사용자 입력 데이터를 받아 서비스 계층으로 전달할때
public class PostDTO {

    // 게시글의 고유 식별자 (Primary Key로 사용)
    //모든 필드는 private로 선언하여 외부클래스로 직접 접근할수 없도록 제한함


    private int postPk;

    // 게시글을 작성한 사용자의 ID
    private String userId;

    // 게시글의 내용
    private String contents;

    // 게시글이 작성된 시간
    private String postTime;

    // 게시글의 위치 정보 (사용자가 위치를 입력한 경우)
    private String location;

    // 게시글의 좋아요 수
    private int likesCount;

    // 기본 생성자 - DTO는 객체를 만들 때 필드 값을 초기화하지 않고 빈 상태로 사용될 수 있기 때문에 필요
    public PostDTO() {
    }

    // 모든 필드의 값을 초기화하는 생성자 - 객체를 생성하면서 필요한 데이터를 한 번에 설정할 수 있게 해줌
    public PostDTO(int postPk, String userId, String contents, String postTime, String location, int likesCount) {
        this.postPk = postPk;
        this.userId = userId;
        this.contents = contents;
        this.postTime = postTime;
        this.location = location;
        this.likesCount = likesCount;
    }

    // 각 필드에 접근할 수 있는 Getter와 Setter 메서드들
    // 외부 클래스에서 직접 필드에 접근하는 것을 막고, 안전하게 필드 값을 설정하거나 가져오기 위해 사용됨
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}

