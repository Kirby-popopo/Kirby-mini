package com.example.Mini1st.dao;

import com.example.Mini1st.domain.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
@Mapper
public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {

    @Select("SELECT * FROM ChatMessage WHERE roomId = #{roomId} ORDER BY sendDate ASC")
    List<ChatMessage> findMessagesByRoomId(int roomId);

    // CrudRepository의 기본 save 메서드를 사용
}
