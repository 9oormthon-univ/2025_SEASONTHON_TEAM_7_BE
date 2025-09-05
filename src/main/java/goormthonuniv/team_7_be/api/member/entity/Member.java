package goormthonuniv.team_7_be.api.member.entity;

import goormthonuniv.team_7_be.common.utils.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@ToString
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 카카오에서 받아온 이메일

    private String nickname; // 추가 정보: 닉네임

    private String interestedJob; // 추가 정보: 관심 직군

    private Double mannerScore; // 매너 점수 (평균 점수)

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
     * 매너 점수 업데이트
     */
    public void updateMannerScore(int score) {
        if (this.mannerScore == null) {
            this.mannerScore = (double)score;
        } else {
            this.mannerScore = (this.mannerScore + score) / 2;
        }
    }

    /**
     * 리프레시 토큰 업데이트
     */
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}