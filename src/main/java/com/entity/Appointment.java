package com.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter

public class Appointment {
    @Id
    private String userid;
    private String bookid;
    private String appointmenttime;
    private String appointstate;

}
