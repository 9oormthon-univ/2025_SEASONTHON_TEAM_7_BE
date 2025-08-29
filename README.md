# TEAM_7_BE


## 1. 프로젝트 환경
- 언어/런타임
  - JDK 21
- 프레임워크 및 빌드
  - Spring Boot 3.5.5
  - Gradle Groovy
- 데이터베이스
  - MySQL 8.0

## 2. 주요 컴포넌트

```plaintext
src/main/java/goormthonuniv/team_7_be/
├── Team7BeApplication.java     
├── api/                        # API 관련 컴포넌트
│   ├── entity/                 # JPA 엔티티 클래스
│   │   ├── Member.java         
│   │   └── MemberRole.java     
│   └── repository/             
│       └── MemberRepository.java
└── common/                     # 공통 컴포넌트
    ├── auth/                   # 인증/인가 관련
    │   ├── config/
    │   │   └── SecurityConfig.java        # Spring Security 설정
    │   ├── exception/
    │   │   └── AuthExceptionType.java     
    │   ├── filter/                        # JWT 인증 필터
    │   │   └── JwtAuthenticationFilter.java 
    │   ├── resolver/
    │   │   ├── Auth.java                  # 인증 어노테이션
    │   │   └── AuthArgumentResolver.java  # 인증 파라미터 리졸버
    │   └── service/
    │       ├── CustomUserDetailsService.java 
    │       └── dto/
    │           └── CustomUserDetails.java    
    ├── config/
    │   └── SwaggerConfig.java             
    ├── exception/                         # 예외 처리 관련
    │   ├── BaseException.java             
    │   ├── BaseExceptionType.java         
    │   └── ExceptionType.java             
    ├── response/                          # API 응답 공통 클래스
    │   ├── ApiResponse.java              
    │   └── PageResponse.java             
    └── utils/
        ├── BaseTimeEntity.java  
        └── JwtProvider.java     
```

## 3. 실행 방법
사전 준비
- JDK 21 설치
- MySQL 실행 (기본 값으로 localhost:3306, DB 스키마: test)
- 환경 변수 설정:
  - DB_USERNAME (기본: root)
  - DB_PASSWORD (기본: 1234)

애플리케이션 기본 경로
- 서버 기본 포트: 8080 (Spring Boot 기본값)
- Context Path: /api (예: API 엔드포인트는 http://localhost:8080/api/...)

Swagger/OpenAPI 문서
- Swagger UI: http://localhost:8080/api/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/api/v3/api-docs
- JWT 인증이 필요한 경우 Swagger 상단의 Authorize 버튼에서 Bearer 토큰 입력