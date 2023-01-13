package com.panda.spring_boot;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panda.spring_boot.domian.Student;
import com.panda.spring_boot.domian.Teacher;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class LocalTest {

    @Test
    public void testRedis() throws Exception {

        String name = "panda man";
        Date birthDay = new Date();

        Student student = new Student();
        student.setValue(name, birthDay);


        System.out.println(new ObjectMapper().writeValueAsString(student));

        System.out.println(JSONObject.toJSONStringWithDateFormat(student, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat));

        /**

         JsonProperty是位于jackson包里面，搭配ObjectMapper().writeValueAsString(实体类)方法使用
         搭配ObjectMapper().readValue(字符串)方法使用，将字符串转换成实体类。

         <dependency>
         <groupId>com.fasterxml.jackson.core</groupId>
         <artifactId>jackson-databind</artifactId>
         <version>版本号</version>
         </dependency>


         JSONField是位于fastjson包里面，搭配JSON.toJSONString(实体类)方法使用，将实体类转换成json字符串。
         搭配JSON.parseObject(字符串,实体类.class)方法使用，将字符串转换成实体类。

         */

    }

    /**
     *  继承测试
     */
    @Test
    public void testInherit() {

        Teacher teacher = new Teacher();
        teacher.setName("hello");
        teacher.setBirthDay(new Date());

        System.out.println(JSONObject.toJSONStringWithDateFormat(
                teacher,
                "yyyy-MM-dd HH:mm:ss",
                SerializerFeature.WriteDateUseDateFormat));

    }

}
