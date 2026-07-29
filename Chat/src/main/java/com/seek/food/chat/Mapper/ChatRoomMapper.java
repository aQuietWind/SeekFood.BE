package com.seek.food.chat.Mapper;

import com.seek.food.dto.Chat.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatRoomMapper {
    public void insert(ChatRoomDTO room);
}
