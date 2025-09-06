package goormthonuniv.team_7_be.common.scheduler;

import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberActiveScheduler {

    private final MemberRepository memberRepository;

    // 3분마다 실행 (밀리초 단위: 1000 * 60 * 3 = 180000)
    @Scheduled(fixedRate = 180000)
    @Transactional
    public void updateInactiveMembers() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(3);

        // lastActiveAt 이 3분 이상 지난 멤버를 inactive 처리
        int updatedCount = memberRepository.updateInactiveMembers(threshold);

        log.info("비활성 처리된 멤버 수: {}", updatedCount);
    }
}
