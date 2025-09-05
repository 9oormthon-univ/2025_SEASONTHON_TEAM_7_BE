package goormthonuniv.team_7_be.api.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goormthonuniv.team_7_be.api.coffee.dto.request.CoffeeChatCreateRequest;
import goormthonuniv.team_7_be.api.coffee.dto.response.CoffeeChatResponse;
import goormthonuniv.team_7_be.api.coffee.entity.CoffeeChat;
import goormthonuniv.team_7_be.api.coffee.entity.CoffeeChatStatus;
import goormthonuniv.team_7_be.api.coffee.exception.CoffeeChatExceptionType;
import goormthonuniv.team_7_be.api.coffee.repository.CoffeeChatRepository;
import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.exception.MemberExceptionType;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.common.exception.BaseException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CoffeeChatService {

    private final MemberRepository memberRepository;
    private final CoffeeChatRepository coffeeChatRepository;

    @Transactional(readOnly = true)
    public List<CoffeeChatResponse> getAllCoffeeChats() {
        List<CoffeeChat> coffeeChats = coffeeChatRepository.findAll();

        return coffeeChats.stream()
            .map(CoffeeChatResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<CoffeeChatResponse> getMyCoffeeChats(String username) {
        Member member = memberRepository.findByEmail(username)
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        List<CoffeeChat> coffeeChats = coffeeChatRepository.findAllBySenderOrReceiver(member, member);

        return coffeeChats.stream()
            .map(CoffeeChatResponse::from)
            .toList();
    }

    public void createCoffeeChat(CoffeeChatCreateRequest request, String username) {
        Member sender = memberRepository.findByEmail(username)
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        Member receiver = memberRepository.findById(request.receiverId())
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        if (sender.getId().equals(receiver.getId())) {
            throw new BaseException(CoffeeChatExceptionType.SELF_COFFEE_CHAT_NOT_ALLOWED);
        }

        if (coffeeChatRepository.existsBySenderAndReceiverAndStatus(sender, receiver, CoffeeChatStatus.REQUESTED)) {
            throw new BaseException(CoffeeChatExceptionType.DUPLICATE_COFFEE_CHAT_REQUEST);
        }

        // TODO: 알림 보내기

        coffeeChatRepository.save(
            CoffeeChat.builder()
                .sender(sender)
                .receiver(receiver)
                .status(CoffeeChatStatus.REQUESTED)
                .build()
        );
    }
}
