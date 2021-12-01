package com.leverx.project.service;

import com.leverx.project.dto.GameObjectDTO;
import com.leverx.project.model.GameObject;

import java.util.List;

public interface GameObjectService {

    GameObject create(GameObjectDTO gameObjectDTO);

    GameObject update(Integer id,GameObject gameObject);

    void delete(Integer id);

    List<GameObject> getAllGameObjectsById(Integer id);

    List<GameObject> getAll();
}
