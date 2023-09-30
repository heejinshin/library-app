package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.user.Fruit;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

//    private final List<User> users = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate  = jdbcTemplate;
    }

    @PostMapping("/user")   //POST /user
    public void saveUser(@RequestBody UserCreateRequest request){
        // users.add(new User(request.getName(), request.getAge());
        String sql = "Insert INTO user (name, age) VALUES (?, ?)";  // Name, age가 유동적으로 입력될 수 있도록 ?로 설정
        jdbcTemplate.update(sql, request.getName(), request.getAge());
    }



    @GetMapping("/user")
    public List<UserResponse> getUsers(){
//        List<UserResponse> responses = new ArrayList<>();
//        for(int i = 0; i < users.size(); i++){
//            responses.add(new UserResponse(i + 1, users.get(i)));
//        }
//        return responses;
        String sql = "Select * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {  // jdbc템플릿의 쿼리가 sql 수행 -> user 정보를 mapRow가 user Response 타입으로 바꿔줌
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }

}
