package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {
    private Long id;
    private String todo;
    private String charge;
    private String password;
    private String createDate;
    private String updateDate;

    public Schedule(ScheduleRequestDto requestDto) {
        this.todo = requestDto.getTodo();
        this.charge = requestDto.getCharge();
        this.password = requestDto.getPassword();
//        this.createDate = requestDto.getCreateDate();
//        this.updateDate = requestDto.getUpdateDate();
    }
}
