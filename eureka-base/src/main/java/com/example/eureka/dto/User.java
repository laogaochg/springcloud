package com.example.eureka.dto;

import io.swagger.annotations.ApiModelProperty;

public class User {
    @ApiModelProperty("ID")
    private String id ;
    @ApiModelProperty(value = "用户名字",required = true)
    private String name;

    public User(String id,String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
