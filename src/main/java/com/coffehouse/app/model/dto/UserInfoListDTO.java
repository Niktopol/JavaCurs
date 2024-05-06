package com.coffehouse.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserInfoListDTO {
    List<UserInfoDTO> infos;

    public UserInfoListDTO() {
        this.infos = new ArrayList<>();
    }
}
