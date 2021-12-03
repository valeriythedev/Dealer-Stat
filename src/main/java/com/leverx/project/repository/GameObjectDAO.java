package com.leverx.project.repository;

import com.leverx.project.model.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameObjectDAO extends JpaRepository<GameObject, Integer> {

    @Query(value = "select c FROM GameObject c JOIN c.users u WHERE u.id = :userId")
    List<GameObject> findAllGameObjectsById(Integer userId);
}
