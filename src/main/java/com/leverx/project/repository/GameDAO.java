package com.leverx.project.repository;

import com.leverx.project.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameDAO extends JpaRepository<Game, Integer> {
}
