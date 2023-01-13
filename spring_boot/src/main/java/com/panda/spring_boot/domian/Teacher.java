package com.panda.spring_boot.domian;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Teacher extends BaseObject{

    @JSONField(name = "BIRTHDAY")
    private Date birthDay;

}
