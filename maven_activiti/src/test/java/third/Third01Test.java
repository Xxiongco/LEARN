package third;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

public class Third01Test {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void deploy() {

        Deployment deploy = processEngine.getRepositoryService().createDeployment()
                .name("third-01")
                .addClasspathResource("third/third-01.bpmn20.xml")
                .deploy();

        System.out.println("部署ID：" + deploy.getId());//1
        System.out.println("部署名称：" + deploy.getName());

    }

    @Test
    public void startInstance() {

        //流程定义的key
        String processDefinitionKey = "third-01";

        String businessKey = "panda business key 12-24";


        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey, businessKey);

        System.out.println("流程实例ID:" + processInstance.getId());
        System.out.println("流程定义ID:" + processInstance.getProcessDefinitionId());

    }

    @Test
    public void findTask() {
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("master")
                //.taskCandidateOrAssigned("\"teacher_1")
                .list();

        for (Task task : tasks) {
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
    public void completeTask() {

        String taskId = "17505";

        processEngine.getTaskService().complete(taskId);

        System.out.println("完成任务：任务ID：" + taskId);

    }


}
