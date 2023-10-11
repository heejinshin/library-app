package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserRepository {


    private final JdbcTemplate jdbcTemplate;
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserNotExist(long id){
        String readSql = "SELECT * FROM user WHERE id = ?";  // 조회용 sql ; 존재하는 user인지
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();  // 비어있다면 UserNotExist가 True인 것으로.-> 0을 반환
    }

    public void updateUserName(String name, long id){
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public boolean isUserNotExist(String name){
        String readSql = "SELECT * FROM user WHERE name = ?";  // 조회용 sql ; 존재하는 user인지
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();  // 비어있다면 UserNotExist가 True인 것으로.
    }

    public void deleteUser(String name){
        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);  // sql 업데이트가 아니라 데이터의 변화가 있는걸 업데이트
    }

    public void saveUser(String name, Integer age){
        String sql = "Insert INTO user (name, age) VALUES (?, ?)";  // Name, age가 유동적으로 입력될 수 있도록 ?로 설정
        jdbcTemplate.update(sql, name, age);
    }

    public List<UserResponse> getUsers() {
        String sql = "Select * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {  // jdbc템플릿의 쿼리가 sql 수행 -> user 정보를 mapRow가 user Response 타입으로 바꿔줌
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }
}
