package com.cricket.repository;

import com.cricket.entity.PlayerCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerCardRepository extends JpaRepository<PlayerCard,Integer> {
}
