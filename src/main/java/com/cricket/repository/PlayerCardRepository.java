package com.cricket.repository;

import com.cricket.entity.PlayerCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface PlayerCardRepository extends JpaRepository<PlayerCard,Integer> {

    PlayerCard findByPlayerId(Integer id);
}
