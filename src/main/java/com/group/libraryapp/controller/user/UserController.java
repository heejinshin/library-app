package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.user.Fruit;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {


    private final UserService userService;
//    private final List<User> users = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate  = jdbcTemplate;
        this.userService = new UserService(jdbcTemplate);
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

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request){

        userService.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {  // 쿼리로 받기때문에 param
        String readSql = "SELECT * FROM user WHERE name = ?";  // 조회용 sql ; 존재하는 user인지
        boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();  // 비어있다면 UserNotExist가 True인 것으로.
        if(isUserNotExist) {
            throw new IllegalArgumentException();
        }

        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);  // sql 업데이트가 아니라 데이터의 변화가 있는걸 업데이트
    }

}
