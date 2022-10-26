package second;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 使用监听器
 */
public class Hello3 {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("hello3")//添加部署的名称
                .addClasspathResource("diagram/hello3.bpmn20.xml")
                //.addClasspathResource("diagrams/helloworld.png")
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署名称：" + deployment.getName());
    }

    /**
     *  设置监听类的时候，不能设置事件类型，设置事件类型，需要设置不同的接口
     */

    /**
     * 部署并启动
     */
    @Test
    public void startProcessInstance() {

        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("hello3")//添加部署的名称
                .addClasspathResource("diagram/hello3.bpmn20.xml")
                //.addClasspathResource("diagrams/helloworld.png")
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());
        System.out.println("部署名称：" + deployment.getName());

        //流程定义的key
        String processDefinitionKey = "hello3";
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonalTask() {
        String assignee = "ding";
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskAssignee(assignee)//指定个人任务查询，指定办理人
//						.taskCandidateUser(candidateUser)//组任务的办理人查询
//						.processDefinitionId(processDefinitionId)//使用流程定义ID查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.executionId(executionId)//使用执行对象ID查询
                /**排序*/
                .orderByTaskCreateTime().asc()//使用创建时间的升序排列
                /**返回结果集*/
//						.singleResult()//返回惟一结果集
//						.count()//返回结果集的数量
//						.listPage(firstResult, maxResults);//分页查询
                .list();//返回列表
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("########################################################");
            }
        }
    }

    /**
     * 完成我的任务
     */
    @Test
    public void completeMyPersonalTask() {
        //任务ID
        String taskId = "75008";
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);
        System.out.println("完成任务：任务ID：" + taskId);
    }

    //可以分配个人任务从一个人到另一个人（认领任务）
    @Test
    public void setAssigneeTask() {
        //任务ID
        String taskId = "5804";
        //指定的办理人
        String userId = "张翠山";
        processEngine.getTaskService()//
                .setAssignee(taskId, userId);
    }
}
