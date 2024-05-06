package com.coffehouse.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class MenuPositionListDTO {
    List<MenuPositionDTO> menuPositions;

    public MenuPositionListDTO(){
        menuPositions = new ArrayList<>();
    }
}
