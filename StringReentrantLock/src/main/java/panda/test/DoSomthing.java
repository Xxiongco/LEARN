package panda.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoSomthing {

    @Autowired
    MyCustomProperties myCustomProperties;

    public void doSomeThing(String str){
        System.out.println(str);
    }
    public void printProperties(){

        System.out.println("address="+myCustomProperties.getAddress());

        System.out.println("name="+myCustomProperties.getName());

    }
}
