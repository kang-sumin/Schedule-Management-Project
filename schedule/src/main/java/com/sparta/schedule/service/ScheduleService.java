package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ScheduleService {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);


        //지금 현재 시간 데이터 저장
        //LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String nowstring = format.format(new Date());

        schedule.setCreateDate(nowstring);
        schedule.setUpdateDate(nowstring);

        // DB 저장
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);

        Schedule saveSchedule = scheduleRepository.save(schedule);

        //Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(saveSchedule);

        return scheduleResponseDto;
    }

    // id를 통한 단건조회
    public List<ScheduleResponseDto> getSchedule(Long id) {
        // DB 조회
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);

        return scheduleRepository.findId(id);
    }

    // 수정일과 담당자를 통한 다건조회
    public List<ScheduleResponseDto> getSchedules(String updateDate, String charge) {
        // DB 조회
       ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);

        return  scheduleRepository.findAll(updateDate, charge);
    }
}
