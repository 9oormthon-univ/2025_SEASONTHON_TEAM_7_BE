package goormthonuniv.team_7_be.api.member.entity;

import goormthonuniv.team_7_be.common.utils.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member") // "user"는 DB 예약어인 경우가 많아 "member" 테이블을 사용하는 것을 권장해요.
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 카카오에서 받아온 이메일

    private String nickname; // 추가 정보: 닉네임

    private String interestedJob; // 추가 정보: 관심 직군

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role; // 사용자 권한 (GUEST, USER)

    private String refreshToken; // 리프레시 토큰

    @Builder
    public Member(String email, MemberRole role) {
        this.email = email;
        this.role = role;
    }
    /**
     * 추가 정보(닉네임, 관심 직군)를 업데이트하고 권한을 GUEST에서 USER로 변경
     */
    public void completeSignUp(String nickname, String interestedJob) {
        this.nickname = nickname;
        this.interestedJob = interestedJob;
        this.role = MemberRole.USER; // 회원가입 완료 시 USER 권한으로 변경
    }

    /**
     * 리프레시 토큰 업데이트
     */
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}