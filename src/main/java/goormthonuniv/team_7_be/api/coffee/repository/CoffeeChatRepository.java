package goormthonuniv.team_7_be.api.coffee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.coffee.entity.CoffeeChat;
import goormthonuniv.team_7_be.api.coffee.entity.CoffeeChatStatus;
import goormthonuniv.team_7_be.api.member.entity.Member;

public interface CoffeeChatRepository extends JpaRepository<CoffeeChat, Long> {

    boolean existsBySenderAndReceiverAndStatus(Member sender, Member receiver, CoffeeChatStatus status);

    List<CoffeeChat> findAllBySenderOrReceiver(Member sender, Member receiver);
}
