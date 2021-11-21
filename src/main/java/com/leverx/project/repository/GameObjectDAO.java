package com.leverx.project.repository;

import com.leverx.project.model.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameObjectDAO extends JpaRepository<GameObject, Integer> {
}
