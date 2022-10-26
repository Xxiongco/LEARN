package second;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  设置多个审批人，那么需要设置addCandidateUser,运行到这个节点的时候，这个时候是没有assigen的，
 *  但是可以通过设置那么需要设置addCandidateUser
 *  去查询任务。
 */

public class Hello4 {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("hello4")//添加部署的名称
                .addClasspathResource("diagram/hello4.bpmn20.xml")
                //.addClasspathResource("diagrams/helloworld.png")
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署名称：" + deployment.getName());
    }

    @Test
    public void startProcessInstance() {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("inputUser", "重要");

        //流程定义的key
        String processDefinitionKey = "hello4";
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    /**
     *
     *  任务图
     *
     *  #{inputUser}  -> panda-1/panda-2  -> ding
     *
     */

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonalTask() {
        String assignee = "重要";
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskAssignee(assignee)//指定个人任务查询，指定办理人
                //.or().taskCandidateUser("zhangsan")//组任务的办理人查询
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

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("pass", "1");
        //任务ID
        String taskId = "200003";
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId, variables);
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
