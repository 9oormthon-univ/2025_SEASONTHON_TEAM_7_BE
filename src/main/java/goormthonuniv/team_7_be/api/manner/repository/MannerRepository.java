package goormthonuniv.team_7_be.api.manner.repository;

import goormthonuniv.team_7_be.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.manner.entity.Manner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MannerRepository extends JpaRepository<Manner, Long> {
    Optional<Manner> findTopByReceiverOrderByCreatedAtDesc(Member receiver);

    @Query("SELECT m FROM Manner m JOIN FETCH m.reviewer WHERE m.receiver = :receiver ORDER BY m.createdAt DESC")
    List<Manner> findAllByReceiverWithReviewer(@Param("receiver") Member receiver);

}
