package goormthonuniv.team_7_be.api.member.entity;

import goormthonuniv.team_7_be.common.utils.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private MemberAge memberAge; // 추가정보 : 연령대

    @ElementCollection(fetch = FetchType.LAZY) // 지연 로딩 설정
    @CollectionTable(
            name = "member_interest", // enum 값들을 저장할 별도의 테이블 이름
            joinColumns = @JoinColumn(name = "member_id") // 해당 테이블에서 Member를 참조하기 위한 외래 키
    )
    @Enumerated(EnumType.STRING) // enum의 이름을 DB에 문자열로 저장
    @Column(name = "interest_name") // enum 값이 저장될 컬럼의 이름
    private List<InterestedJob> interests = new ArrayList<>(); // 관심사 목록

    private String introduceMySelf; // 추가정보 : 자기소개란

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

    /*
    피그마 반영 최신본
     */
    public void completeSignUp(String nickname, List<InterestedJob> interests, String introduceMySelf, MemberAge memberAge) {
        this.nickname = nickname;
        this.introduceMySelf = introduceMySelf;
        this.memberAge = memberAge;
        this.role = MemberRole.USER;
        this.interests = interests; // 전달받은 리스트로 바로 교체
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
    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /**
     * 리프레시 토큰 업데이트
     */
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}