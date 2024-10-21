package mybatis.dao.login;

import com.example.Mini1st.dao.login.UserAuthenticateDTO;
import com.example.Mini1st.dao.login.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    // 회원가입
    @Insert("insert into users (user_id, user_pw, email, name, gender, nickname, phone_number, last_login)" +
            "values (#{userId}, #{userPw}, #{email}, #{name}, #{gender}, #{nickname}, #{phoneNumber}, #{lastLogin})")
    boolean insert(UserAuthenticateDTO dto);
    // 아이디로 사용자 조회
    @Select("SELECT user_id FROM users WHERE user_id = #{userId}")
    UserDTO getUserById(String userId);

    // 모든 사용자 조회
    @Select("SELECT * FROM users")
    List<UserDTO> getAllUsers();

    // 사용자 업데이트
    @Update("UPDATE users SET user_pw = #{userPw}, email = #{email}, name = #{name}, gender = #{gender}, " +
            "nickname = #{nickname}, phone_number = #{phoneNumber}, profile_image = #{profileImage} WHERE user_id = #{userId}")
    void updateUser(UserDTO user);

    // 사용자 삭제 나중에 바꿔야함
    @Delete("DELETE FROM users WHERE user_id = #{userId}")
    void deleteUser(String userId);

    // 로그인 인증
    @Select("SELECT user_id, user_pw FROM users WHERE user_id = #{userId} AND user_pw = #{userPw}")
    UserDTO getUserByIdAndPw(@Param("userId") String userId, @Param("userPw") String userPw);

    // 아이디 중복체크
    @Select("SELECT COUNT(user_id) FROM users WHERE user_id = #{userId}")
    public int idCheck(String userId);
}