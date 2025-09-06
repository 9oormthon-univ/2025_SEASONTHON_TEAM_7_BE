package goormthonuniv.team_7_be.api.member.service;

import goormthonuniv.team_7_be.api.coffee.entity.CoffeeChat;
import goormthonuniv.team_7_be.api.coffee.exception.CoffeeChatExceptionType;
import goormthonuniv.team_7_be.api.coffee.repository.CoffeeChatRepository;
import goormthonuniv.team_7_be.api.member.dto.MemberProfileDto;
import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.entity.MemberRole;
import goormthonuniv.team_7_be.api.member.exception.MemberExceptionType;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CoffeeChatRepository coffeeChatRepository;

    public List<MemberProfileDto> findAllMemberProfiles(String username) {
        List<Member> members = memberRepository.findAllNotInEmailAndRole(username, MemberRole.USER);
        return members.stream()
                .map(MemberProfileDto::from)
                .toList();
    }

    public MemberProfileDto findMemberProfileById(String username) {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));
        return MemberProfileDto.from(member);
    }

    public MemberProfileDto findMemberProfile(Long coffeeChatId) {
        CoffeeChat coffeeChat = coffeeChatRepository.findById(coffeeChatId)
                .orElseThrow(() -> new BaseException(CoffeeChatExceptionType.COFFEE_CHAT_NOT_FOUND));

        Member member = memberRepository.findById(coffeeChat.getSender().getId())
                .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        return MemberProfileDto.from(member);
    }
}
