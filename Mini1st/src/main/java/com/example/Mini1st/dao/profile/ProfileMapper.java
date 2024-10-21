package com.example.Mini1st.dao.profile;

import com.example.Mini1st.dao.login.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**************************************************
 *           profile mapper
 *     1. 유저의 데이터를 가져오기. (프로필 사진, 유저 이름)
 *     2. 게시글 가져오기. (유저의 게시글 전부 가져오기)
 *     3. 팔로우, 팔로잉 가져오기.
 ***************************************************/

@Mapper
public interface ProfileMapper {
    @Select("select user_id, name, description, profile_image from users where user_id = #{id}")
    public UserDTO findUser(String id);

    @Select("select post_pk, user_id, contents, image_link from posts where user_id = #{id}")
    public List<PostDTO> findUserPost(String id);

    /**
     * 나를 팔로잉 하고 있는 유저를 찾아서 반환합니다.
     * @param id : 내 유저 아이디
     * @return : 내가 친구추가한 유저들
     */
    @Select("select following_id from follows where follower_id = #{id}")
    public List<FollowingDTO> findFollowingUser(String id);

    /**
     * 나를 팔로우 하고 있는 유저를 찾아서 반환합니다.
     * @param id : 내 유저 아이디
     * @return : 날 친구추가한 유저들
     */
    @Select("select follower_id from follows where following_id = #{id}")
    public List<FollowDTO> findFollowUser(String id);
}
