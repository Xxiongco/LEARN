package com.panda.spring_boot.domian;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Student {

    private String name;

    private Date birthDay;

    @JsonProperty("STUDENT-NAME-ONE")
    private String nameOne;

    @JsonProperty("BIRTHDAY-ONE")
    private Date birthDayOne;

    @JSONField(name = "STUDENT-NAME-TWO")
    private String nameTWO;

    @JSONField(name = "BIRTHDAY-ONE")
    private Date birthDayTWO;


    public void setValue(String name, Date birthDay) {
        this.name = name;
        this.nameOne = name;
        this.nameTWO = name;

        this.birthDay = birthDay;
        this.birthDayOne = birthDay;
        this.birthDayTWO = birthDay;


    }

}
