package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 생성
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // 객체간 이동 위해 ScheduleService 객체 생성
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);

        return scheduleService.createSchedule(requestDto);
    }

    // 일정 조회 (단건)
    @GetMapping("/schedules/{id}")
    public List<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        // 객체간 이동 위해 ScheduleService 객체 생성
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);

        return scheduleService.getSchedule(id);
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules(@RequestParam(required = false) String updateDate, @RequestParam(required = false) String charge){
        //객체간 이동 위한 ScheduleService 객체 생성
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);

        return scheduleService.getSchedules(updateDate,charge);
    }

}
