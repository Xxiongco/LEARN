package panda.test;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"panda.test"})
@EnableConfigurationProperties
public class AutoConfig {

}
