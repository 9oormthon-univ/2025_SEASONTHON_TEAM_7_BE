package goormthonuniv.team_7_be.api.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.member.entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
