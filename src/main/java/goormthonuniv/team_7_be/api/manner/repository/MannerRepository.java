package goormthonuniv.team_7_be.api.manner.repository;

import goormthonuniv.team_7_be.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.manner.entity.Manner;

import java.util.Optional;

public interface MannerRepository extends JpaRepository<Manner, Long> {
    Optional<Manner> findTopByReceiverOrderByCreatedAtDesc(Member receiver);
}
