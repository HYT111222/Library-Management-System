package com.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class Library {
    @Id
    public String libraryid;
    public String opentime;
    public String closetime;
    public int chinesebooknumber;
    public int foreignbooknumber;
}
