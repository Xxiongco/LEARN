package learn;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -2);

        Date time = calendar.getTime();

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = fmt.format(time);
        System.out.println(time);
        System.out.println(format);

    }


    @org.junit.jupiter.api.Test
    public void test2() {
        List<String> statusLookupCodeList = Arrays.asList("YXK", "YJH", "YZJ", "YSPA",
                "BGXK", "BGQF", "YQXK", "YQPZ", "JHJS", "YZJY", "YZJN", "DZJ");

        String collect = String.join(",", statusLookupCodeList);

        System.out.println(collect);


    }



}
