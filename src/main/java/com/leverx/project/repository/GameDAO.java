package com.leverx.project.repository;

import com.leverx.project.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDAO extends JpaRepository<Game, Integer> {
}
