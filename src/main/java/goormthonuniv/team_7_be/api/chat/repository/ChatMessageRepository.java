package goormthonuniv.team_7_be.api.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.chat.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoomIdOrderByCreatedAtAsc(Long chatRoomId);
}
