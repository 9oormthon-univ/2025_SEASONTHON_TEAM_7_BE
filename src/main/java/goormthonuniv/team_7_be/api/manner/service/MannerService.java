package goormthonuniv.team_7_be.api.manner.service;

import goormthonuniv.team_7_be.api.chat.exception.ChatExceptionType;
import goormthonuniv.team_7_be.api.chat.repository.ChatRoomRepository;
import goormthonuniv.team_7_be.api.manner.dto.request.MannerCreateRequest;
import goormthonuniv.team_7_be.api.manner.dto.response.MannerResponse;
import goormthonuniv.team_7_be.api.manner.entity.Manner;
import goormthonuniv.team_7_be.api.manner.exception.MannerExceptionType;
import goormthonuniv.team_7_be.api.manner.repository.MannerRepository;
import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.exception.MemberExceptionType;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.api.notification.event.MannerReviewCreatedEvent;
import goormthonuniv.team_7_be.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MannerService {

    private final MannerRepository mannerRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MannerResponse createManner(String email, MannerCreateRequest request) {
        Member reviewer = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        if (reviewer.getId().equals(request.memberId())) {
            throw new BaseException(MannerExceptionType.CANNOT_EVALUATE_YOURSELF);
        }

        // 나와 상대방이 채팅을 한 기록이 있는지 확인
        if (!chatRoomRepository.existsByIdAndOpponentId(reviewer.getId(), request.memberId())) {
            throw new BaseException(ChatExceptionType.CHAT_ROOM_NOT_FOUND);
        }

        Member targetMember = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        // 매너 평가 생성
        targetMember.updateMannerScore(request.rate());
        Manner manner = mannerRepository.save(
                Manner.builder()
                        .receiver(targetMember)
                        .reviewer(reviewer)
                        .rate(request.rate())
                        .review(request.review())
                        .build()
        );

        // 이벤트 발행: 매너 후기 작성 알림
        eventPublisher.publishEvent(new MannerReviewCreatedEvent(reviewer, targetMember, request.rate(), request.review()));

        return MannerResponse.from(manner);
    }
}
