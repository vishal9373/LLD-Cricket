package com.cricket.repository;

import com.cricket.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    List<Player> findByGameId(Integer id);
}
