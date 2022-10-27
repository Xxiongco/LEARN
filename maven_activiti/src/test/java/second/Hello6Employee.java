package second;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
public class Hello6Employee implements Serializable {
    public List<String> getEmployee() {
        return Arrays.asList("xiong","hong","ding");
    }
}
