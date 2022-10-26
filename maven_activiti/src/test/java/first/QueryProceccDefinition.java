package first;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 流程定义查询
 *
 * @author zrj
 * @date 2020/12/29
 * @since V1.0
 **/
public class QueryProceccDefinition {

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

    /**
     * 删除指定流程id的流程
     */
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

    /**
     * 获取资源
     */
    @Test
    public void getProcessResources() throws IOException {
        // 流程定义id
        String processDefinitionId = "";
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 获取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 流程定义对象
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        //获取bpmn
        String resource_bpmn = processDefinition.getResourceName();
        //获取png
        String resource_png = processDefinition.getDiagramResourceName();
        // 资源信息
        System.out.println("bpmn： " + resource_bpmn);
        System.out.println("png： " + resource_png);
        File file_png = new File("d:/purchasingflow01.png");
        File file_bpmn = new File("d:/purchasingflow01.bpmn");
        // 输出bpmn
        InputStream resourceAsStream = null;
        resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resource_bpmn);
        FileOutputStream fileOutputStream = new FileOutputStream(file_bpmn);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            fileOutputStream.write(b, 0, len);
        }
        // 输出图片
        resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resource_png);
        fileOutputStream = new FileOutputStream(file_png);
        // byte[] b = new byte[1024];
        // int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            fileOutputStream.write(b, 0, len);
        }
    }


}
