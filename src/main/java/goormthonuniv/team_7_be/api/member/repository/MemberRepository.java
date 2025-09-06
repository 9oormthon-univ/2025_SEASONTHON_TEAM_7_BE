package goormthonuniv.team_7_be.api.member.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import goormthonuniv.team_7_be.api.member.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.member.entity.Member;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.email <> :email ORDER BY m.createdAt DESC")
    List<Member> findAllNotInEmailAndRoleOrderByLastActiveAtDesc(@Param("email") String email,  @Param("role") MemberRole role);

    Optional<Member> findByEmail(String email);

    Boolean existsByNickname(String nickname);

    @Modifying
    @Query("UPDATE Member m SET m.isActive = false WHERE m.lastActiveAt < :threshold AND m.isActive = true")
    int updateInactiveMembers(@Param("threshold") LocalDateTime threshold);
}
