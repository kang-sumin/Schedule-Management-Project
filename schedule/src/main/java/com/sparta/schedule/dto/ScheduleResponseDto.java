package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String todo;
    private String charge;
    private String createDate;
    private String updateDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.charge = schedule.getCharge();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }

    public ScheduleResponseDto(Long id, String todo, String charge, String createDate, String updateDate) {
        this.id = id;
        this.todo = todo;
        this.charge = charge;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
