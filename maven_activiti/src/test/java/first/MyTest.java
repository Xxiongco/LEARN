package first;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.List;

public class MyTest {

    /**
     *  初始化
     */
    @Test
    public void GenActivitiTables2() {
        //条件：1.activiti配置文件名称：activiti.cfg.xml   2.bean的id="processEngineConfiguration"
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }

    /**
     *  部署文件，部署了一个文件，意味这部署了一个流程，这个流程就定了。
     *  每启动一个流程，那么就按照这流程走了。
     *
     *  那么怎么找到这个文件对应的流程呢？
     *  文件的名，也就是processDefinitionKey
     *
     */
    @Test
    public void activitiDeploymentTest() {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagram/leave.bpmn20.xml")
                //.addClasspathResource( "diagram/leave.bpmn20.png" )
                .name("请假申请单流程")
                .deploy();

        //4.输出部署的一些信息
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());

    }

    /**
     *  查询
     */
    @Test
    public void queryProceccDefinition() {
        // 流程定义key
        String processDefinitionKey = "leave";
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

    @Test
    public void deleteDeployment() {
        // 流程部署id
        String deploymentId = "1";
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 通过流程引擎获取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //删除流程定义， 如果该流程定义已有流程实例启动则删除时出错
        repositoryService.deleteDeployment(deploymentId);
        //设置true 级联删除流程定义，即使该流程有流程实例启动也可以删除，设
        //置为false非级别删除方式，如果流程
        repositoryService.deleteDeployment(deploymentId, true);
    }

    @Test
    public void startInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RunService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //3.创建流程实例  流程定义的key需要知道 leave
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave");

        //4.输出实例的相关信息
        System.out.println("流程部署ID" + processInstance.getDeploymentId());
        System.out.println("流程定义ID" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例ID" + processInstance.getId());
        System.out.println("活动ID" + processInstance.getActivityId());

    }


}
