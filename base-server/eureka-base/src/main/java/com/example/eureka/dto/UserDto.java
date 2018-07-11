package com.example.eureka.dto;

import lombok.Data;

@Data
public class UserDto {
    private String id ;

    private String name;

    public UserDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDto() {
    }

}
