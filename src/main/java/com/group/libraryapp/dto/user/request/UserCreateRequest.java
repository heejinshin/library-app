package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {

    private String name;
    private Integer age;  // int는 null을 표현할 수 없음.

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
