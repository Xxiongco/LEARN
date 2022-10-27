package learn;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class Student {

private int age=10;

private String name="hhh";

  public List<String> list;

  public Date time=new Date();
}
