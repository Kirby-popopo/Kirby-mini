package com.example.Mini1st.dao;

import com.example.Mini1st.domain.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface ChatMessageRepository{

    @Select("SELECT * FROM ChatMessage WHERE roomId = #{roomId} ORDER BY sendDate ASC")
    List<ChatMessage> findMessagesByRoomId(int roomId);
}
