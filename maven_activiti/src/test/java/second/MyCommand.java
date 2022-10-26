package second;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import java.io.Serializable;

public class MyCommand implements Command<String>, Serializable {
 
	public String execute(CommandContext commandContext) {
		System.out.println("Hello world");
		return null;
	}
}