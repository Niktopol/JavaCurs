package com.coffehouse.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MenuPositionDTO {
    Long positionId;
    int count;

    public MenuPositionDTO(){
        this.positionId = -1L;
        this.count = 0;
    }
}
