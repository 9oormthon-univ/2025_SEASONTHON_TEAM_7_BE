package goormthonuniv.team_7_be.api.main_page_list.service;

import goormthonuniv.team_7_be.api.main_page_list.dto.MemberProfileDto;
import goormthonuniv.team_7_be.api.member.entity.Member;
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
public class MemberListService {

    private final MemberRepository memberRepository;

    public List<MemberProfileDto> findAllMemberProfiles(String username) {
        List<Member> members = memberRepository.findAllNotInEmail(username);
        return members.stream()
                .map(MemberProfileDto::from)
                .toList();
    }

    public MemberProfileDto findMemberProfileById(String username) {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));
        return MemberProfileDto.from(member);
    }
}
