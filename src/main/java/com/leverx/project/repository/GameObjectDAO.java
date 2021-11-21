package com.leverx.project.repository;

import com.leverx.project.model.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameObjectDAO extends JpaRepository<GameObject, Integer> {
}
