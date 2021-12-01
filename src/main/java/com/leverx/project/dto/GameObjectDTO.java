package com.leverx.project.dto;

import com.leverx.project.model.GameObject;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameObjectDTO {

    private Integer id;
    private String title;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Integer author_id;
    private Integer game_id;

    public GameObject toGameObject(GameObjectDTO gameObjectDTO) {
        GameObject gameObject = new GameObject();
        gameObject.setId(gameObjectDTO.getId());
        gameObject.setTitle(gameObjectDTO.getTitle());
        gameObject.setCreated_at(gameObjectDTO.getCreated_at());
        gameObject.setUpdated_at(gameObjectDTO.getUpdated_at());
        return gameObject;
    }

}
