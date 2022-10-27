package learn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Data;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class JsonObjectLearn {

    public ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void test() throws JsonProcessingException {
        List<String> list = new ArrayList<>();
        list.add("xiong");
        list.add("hong");
        list.add("ding");

        Student student = new Student();
        student.setList(list);

        String s = objectMapper.writeValueAsString(student);

        JsonNode jsonNode = objectMapper.readTree(s);

        System.out.println(jsonNode);

    }

}
