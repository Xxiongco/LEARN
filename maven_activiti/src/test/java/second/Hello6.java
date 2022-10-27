package second;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  基础的工作流接口
 */
public class Hello6 {


    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    public List<String> assigneeList = Arrays.asList("张三", "lisi", "wangwu");

    @Test
    public void GenActivitiTables2() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }

    /**
     * 部署流程定义
     * 可以理解为流程的定义（需要部署），流程启动那么就是一个实例
     */
    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("hello6")//添加部署的名称
                .addClasspathResource("diagram/hello6.bpmn20.xml")
                //.addClasspathResource("diagrams/helloworld.png")
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署名称：" + deployment.getName());
    }


    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {

        //流程定义的key
        String processDefinitionKey = "hello6";

        HashMap<String, Object> variable = new HashMap<>();
        variable.put("employee",new Hello6Employee());
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey, variable);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }


    // ${employee.getEmployee()}

    /**
     * 完成我的任务
     */
    @Test
    public void completeMyPersonalTask() {
        //任务ID
        String taskId = "282508";

        TaskService taskService = processEngine.getTaskService();
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);
        System.out.println("完成任务：任务ID：" + taskId);
    }

}