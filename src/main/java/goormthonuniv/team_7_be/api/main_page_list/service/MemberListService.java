package goormthonuniv.team_7_be.api.main_page_list.service;

import goormthonuniv.team_7_be.api.main_page_list.dto.MemberProfileDto;
import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberListService {
    private final MemberRepository memberRepository;

    // ★ 파라미터가 없고, 항상 전체 목록을 조회하는 메서드
    public List<MemberProfileDto> findAllMemberProfiles() {

        // ★ 필터링 로직 없이 바로 전체 회원 조회
        List<Member> members = memberRepository.findAll();

        // 조회된 엔티티 목록을 DTO 목록으로 변환
        return members.stream()
                .map(MemberProfileDto::from)
                .collect(Collectors.toList());
    }
}
