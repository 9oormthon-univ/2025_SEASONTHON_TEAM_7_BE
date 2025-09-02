package goormthonuniv.team_7_be.api.chat.repository;

import goormthonuniv.team_7_be.api.chat.entity.ChatMessage;
import goormthonuniv.team_7_be.api.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomIdOrderByCreatedAtAsc(Long chatRoomId);
    ChatMessage findTopByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);

}
