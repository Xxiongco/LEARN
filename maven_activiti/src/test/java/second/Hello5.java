package second;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.HashMap;

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
        variable.put("name","xiong,hong,ding,he,ling,feng");
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey, variable);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

}
