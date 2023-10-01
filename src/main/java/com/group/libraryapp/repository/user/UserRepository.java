package com.group.libraryapp.repository.user;

import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserRepository {

    public boolean isUserNotExist(JdbcTemplate jdbcTemplate, long id){
        String readSql = "SELECT * FROM user WHERE id = ?";  // 조회용 sql ; 존재하는 user인지
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();  // 비어있다면 UserNotExist가 True인 것으로.-> 0을 반환
    }

    public void updateUserName(JdbcTemplate jdbcTemplate, String name, long id){
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }
}
