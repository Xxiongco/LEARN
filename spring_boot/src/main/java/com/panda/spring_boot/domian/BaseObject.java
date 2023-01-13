package com.panda.spring_boot.domian;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class BaseObject {

    @JSONField(name = "NAME")
    private String name;

}
