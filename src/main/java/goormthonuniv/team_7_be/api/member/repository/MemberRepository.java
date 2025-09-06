package goormthonuniv.team_7_be.api.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.member.entity.Member;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.email <> :email ORDER BY m.createdAt DESC")
    List<Member> findAllNotInEmail(@Param("email") String email);
    Optional<Member> findByEmail(String email);
    Boolean existsByNickname(String nickname);
}
