package second;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;


/**
 *  基础的工作流接口
 */
public class Hello {

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
                .name("helloworld入门程序")//添加部署的名称
                .addClasspathResource("diagram/hello.bpmn20.xml")
                //.addClasspathResource("diagrams/helloworld.png")
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署名称：" + deployment.getName());
    }

    /**
     * 这个只能查部署的流程定义
     */
    @Test
    public void queryProceccDefinition() {
        // 流程定义key
        String processDefinitionKey = "hello";
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 查询流程定义
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //遍历查询结果
        List<ProcessDefinition> list = processDefinitionQuery
                .processDefinitionKey(processDefinitionKey)
                .orderByProcessDefinitionVersion().desc().list();

        for (ProcessDefinition processDefinition : list) {
            System.out.println("------------------------");
            System.out.println(" 流 程 部 署 id ： " + processDefinition.getDeploymentId());
            System.out.println("流程定义id： " + processDefinition.getId());
            System.out.println("流程定义名称： " + processDefinition.getName());
            System.out.println("流程定义key： " + processDefinition.getKey());
            System.out.println("流程定义版本： " + processDefinition.getVersion());
        }
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        //流程定义的key
        String processDefinitionKey = "hello";

        HashMap<String, Object> variable = new HashMap<>();
        variable.put("one","1");
        variable.put("two", "2");
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey, variable);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }


    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonalTask() {

        for (String assignee : assigneeList) {
            System.out.println("########################################################");
            System.out.println("assignee : " + assignee);
            List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .createTaskQuery()//创建任务查询对象
                    .taskAssignee(assignee)//指定个人任务查询，指定办理人
                    .list();
            if (list != null && list.size() > 0) {
                System.out.println("-----------------");
                for (Task task : list) {
                    System.out.println("任务ID:" + task.getId());
                    System.out.println("任务名称:" + task.getName());
                    System.out.println("任务的创建时间:" + task.getCreateTime());
                    System.out.println("任务的办理人:" + task.getAssignee());
                    System.out.println("流程实例ID：" + task.getProcessInstanceId());
                    System.out.println("执行对象ID:" + task.getExecutionId());
                    System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                    System.out.println("-----------------");
                }
            }
        }
    }

    /**
     *  查询正在处理的节点
     */
    @Test
    public void queryActive() {
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                //.processInstanceBusinessKey() 根据BusinessKey去查询数据
                //.active() 活着的节点
                .processDefinitionId("hello:1:3") // 流程定义返回的Id
                .list();

        if (list != null && list.size() > 0) {
            System.out.println("-----------------");
            for (Task task : list) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("-----------------");
            }
        }
    }



    /**
     * 完成我的任务
     */
    @Test
    public void completeMyPersonalTask() {
        //任务ID
        String taskId = "187502";

        TaskService taskService = processEngine.getTaskService();
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);


        System.out.println("完成任务：任务ID：" + taskId);
    }

    @Test
    public void test() {
        String processInstanceId = "177501";
        List<Task> list = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();

        for (Task task : list) {
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务名称:" + task.getName());
            System.out.println("任务的创建时间:" + task.getCreateTime());
            System.out.println("任务的办理人:" + task.getAssignee());
            System.out.println("流程实例ID：" + task.getProcessInstanceId());
            System.out.println("执行对象ID:" + task.getExecutionId());
            System.out.println("流程定义ID:" + task.getProcessDefinitionId());
            System.out.println("-----------------");

        }
    }

    @Test
    public void testVariable() {

        String taskId = "177507";

        Map<String, Object> variables = processEngine.getTaskService().getVariables(taskId);

        for (Map.Entry<String, Object> item : variables.entrySet()) {
            System.out.println(item.getKey() + ":" + item.getValue());
        }
        Map<String, Object> variable = new HashMap<>();
        variable.put("one","3");
        variable.put("four", "4");
        variable.put("student", new Student("xiong", 20L));

        //processEngine.getTaskService().setVariables(taskId,variable);

    }

    @Data
    @AllArgsConstructor
    public static class Student implements Serializable {

        String name;

        Long age;

    }







}