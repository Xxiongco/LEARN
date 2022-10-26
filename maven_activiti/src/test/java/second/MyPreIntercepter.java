package second;

import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;

public class MyPreIntercepter extends AbstractCommandInterceptor {
 
	public <T> T execute(CommandConfig config, Command<T> command) {
		System.out.println("MyPreIntercepter: execute start");
		next.execute(config, command);
		System.out.println("MyPreIntercepter: execute end");
		return null;
	}
}