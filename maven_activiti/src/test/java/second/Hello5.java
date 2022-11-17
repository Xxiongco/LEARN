package second;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.TreeValueExpression;
import de.odysseus.el.util.SimpleContext;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Hello5 {
    /**
     *  测试流程变量是个List的时候，审批是怎么样的
     */

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("hello5")//添加部署的名称
                .addClasspathResource("diagram/hello5.bpmn20.xml")
                //.addClasspathResource("diagrams/helloworld.png")
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署名称：" + deployment.getName());
    }

    @Test
    public void startProcessInstance() {
        //流程定义的key
        String processDefinitionKey = "hello5";

        HashMap<String, Object> variable = new HashMap<>();
        // 这样是不行的
       variable.put("names","xiong,hong,ding,he,ling,feng");
//        List<String> list = new ArrayList<>();
//        list.add("xiong");
//        list.add("hong");
//        variable.put("names",list);
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey, variable);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }
    /**
     * 完成我的任务
     */
    @Test
    public void completeMyPersonalTask() {
        //任务ID
        String taskId = "235006";

        TaskService taskService = processEngine.getTaskService();
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);

        System.out.println("完成任务：任务ID：" + taskId);
    }

    @Test
    public void testExpression() {
        ExpressionFactoryImpl expressionFactory = new ExpressionFactoryImpl();
        SimpleContext simpleContext = new SimpleContext();
        simpleContext.setVariable("a", expressionFactory.createValueExpression(1000, String.class));
        TreeValueExpression valueExpression = expressionFactory.createValueExpression(simpleContext, "${a >= 1000 }", boolean.class);

        System.out.println(valueExpression.getValue(simpleContext));

    }

}
