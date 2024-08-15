package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //DB 저장
    public Schedule save(Schedule schedule) {
        //DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체


        String sql = "INSERT INTO schedule (todo, charge, password, createDate, updateDate) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getTodo());
                    preparedStatement.setString(2, schedule.getCharge());
                    preparedStatement.setString(3, schedule.getPassword());
                    preparedStatement.setString(4, schedule.getCreateDate());
                    preparedStatement.setString(5, schedule.getUpdateDate());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        return schedule;
    }


    public List<ScheduleResponseDto> findId(Long id) {
        // DB Id 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 결과로 받아온 Schedule 데이터를 ScheduleResponseDto 타입으로 변환해주는 메서드
                Long id = rs.getLong("id");
                String todo = rs.getString("todo");
                String charge = rs.getString("charge");
                String createDate = rs.getString("createDate");
                String updateDate = rs.getString("updateDate");
                return new ScheduleResponseDto(id, todo, charge, createDate, updateDate);
            }
        },id);
    }
}
