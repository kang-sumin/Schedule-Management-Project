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

    // 일정 수정
    public Long updateSchedule(Long id,ScheduleRequestDto scheduleRequestDto) {
        // DB 수정
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);

        String password = scheduleRequestDto.getPassword();

        Schedule schedule = scheduleRepository.findById(id, password);
        if(schedule != null){
            scheduleRepository.update(id, scheduleRequestDto);

            return id;
        }else{
            throw new IllegalArgumentException("선택하신 일정은 존재하지 않습니다.");
        }
    }

    public Long deleteSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        // DB 삭제
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);

        String password = scheduleRequestDto.getPassword();

        Schedule schedule = scheduleRepository.findById(id, password);
        if(schedule != null){
            scheduleRepository.delete(id);

            return id;
        }else{
            throw new IllegalArgumentException("선택하신 일정은 존재하지 않습니다.");
        }
    }
}
