package com.example.Mini1st.dao;

import com.example.Mini1st.domain.ChatRoom;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
public interface ChatRoomRepository {

    @Select("select * from chatRoom")
    public List<ChatRoom> findAllRoom();

    @Select("SELECT * FROM ChatRoom WHERE roomId = #{roomId}")
    ChatRoom findByRoomId(int roomId);

    @Insert("INSERT INTO ChatRoom (room_name) VALUES (#{room_name})")
    @Options(useGeneratedKeys = true, keyProperty = "roomId")
    void save(ChatRoom chatRoom);

}
