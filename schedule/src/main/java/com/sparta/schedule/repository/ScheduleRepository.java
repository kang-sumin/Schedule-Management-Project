package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleRequestDto;
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

    // id를 통한 단건 조회
    public List<ScheduleResponseDto> findId(Long id) {
        // DB 에서 ID로 찾아내는 쿼리
        String sql = "SELECT * FROM schedule WHERE id = ?";
        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String todo = rs.getString("todo");
                String charge = rs.getString("charge");
                String createDate = rs.getString("createDate");
                String updateDate = rs.getString("updateDate");
                return new ScheduleResponseDto(id, todo, charge, createDate, updateDate);
            }
        }, id);
    }

    // 수정일과 담당자를 통한 다건 조회
    // 수정일을 기준으로 내림차순 정렬
    public List<ScheduleResponseDto> findAll(String updateDate, String charge) {
        String sql = "";

        if (updateDate != null && charge != null) {
            sql = "SELECT * FROM schedule WHERE DATE_FORMAT(updateDate, '%Y-%m-%d') = ? AND charge = ? ORDER BY updateDate DESC";

            return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
                @Override
                public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                    Long id = rs.getLong("id");
                    String todo = rs.getString("todo");
                    String charge = rs.getString("charge");
                    String createDate = rs.getString("createDate");
                    String updateDate = rs.getString("updateDate");
                    return new ScheduleResponseDto(id, todo, charge, createDate, updateDate);
                }
            }, updateDate, charge);

        } else if (updateDate == null && charge != null) {
            sql = "SELECT * FROM schedule WHERE charge = ? ORDER BY updateDate DESC";

            return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
                @Override
                public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                    Long id = rs.getLong("id");
                    String todo = rs.getString("todo");
                    String charge = rs.getString("charge");
                    String createDate = rs.getString("createDate");
                    String updateDate = rs.getString("updateDate");
                    return new ScheduleResponseDto(id, todo, charge, createDate, updateDate);
                }
            }, charge);

        } else if (updateDate != null) {
            sql = "SELECT * FROM schedule WHERE DATE_FORMAT(updateDate, '%Y-%m-%d') = ? ORDER BY updateDate DESC";

            return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
                @Override
                public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                    Long id = rs.getLong("id");
                    String todo = rs.getString("todo");
                    String charge = rs.getString("charge");
                    String createDate = rs.getString("createDate");
                    String updateDate = rs.getString("updateDate");
                    return new ScheduleResponseDto(id, todo, charge, createDate, updateDate);
                }
            }, updateDate);

        } else {
            sql = "SELECT * FROM schedule ORDER BY updateDate DESC";

            return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
                @Override
                public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                    Long id = rs.getLong("id");
                    String todo = rs.getString("todo");
                    String charge = rs.getString("charge");
                    String createDate = rs.getString("createDate");
                    String updateDate = rs.getString("updateDate");
                    return new ScheduleResponseDto(id, todo, charge, createDate, updateDate);
                }
            });

        }
    }

    public void update(Long id, ScheduleRequestDto scheduleRequestDto) {
        String sql = "UPDATE schedule SET todo = ?, charge = ?, updateDate = ? WHERE id = ?";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String nowstring = format.format(new Date());
        jdbcTemplate.update(sql, scheduleRequestDto.getTodo(), scheduleRequestDto.getCharge(), nowstring, id);


    }

    // 일정 삭제
    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

    public Schedule findById(Long id, String password) {
        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {

            if (resultSet.next()) {
                Schedule schedule = new Schedule();
                if(password.equals(resultSet.getString("password"))){
                    schedule.setTodo(resultSet.getString("todo"));
                    schedule.setCharge(resultSet.getString("charge"));
                    schedule.setCreateDate(resultSet.getString("createDate"));
                    schedule.setUpdateDate(resultSet.getString("updateDate"));
                }else{
                    throw new IllegalArgumentException("비밀번호가 일치하기 않습니다.");
                }
                return schedule;
            } else {
                return null;
            }
        }, id);
    }

}
