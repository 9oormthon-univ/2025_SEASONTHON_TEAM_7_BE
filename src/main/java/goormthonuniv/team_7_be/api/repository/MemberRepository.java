package goormthonuniv.team_7_be.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
}
