package first;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 流程定义的部署
 * activiti表有哪些？
 * act_re_deployment  部署信息
 * act_re_procdef     流程定义的一些信息
 * act_ge_bytearray   流程定义的bpmn文件及png文件
 *
 * @author zrj
 * @date 2020/12/29
 * @since V1.0
 **/
public class ActivitiDeployment {

    /**
     * 方式一
     * 分别将 bpmn 文件和 png 图片文件部署
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
        // 请假申请单流程
        //1

    }

    /**
     * 方式二
     * 将 holiday.bpmn 和 holiday.png 压缩成 zip 包
     */
    @Test
    public void activitiDeploymentTest2() {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.转化出ZipInputStream流对象
        InputStream is = ActivitiDeployment.class.getClassLoader().getResourceAsStream("diagram/holidayBPMN.zip");

        //将 inputstream流转化为ZipInputStream流
        ZipInputStream zipInputStream = new ZipInputStream(is);

        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("请假申请单流程")
                .deploy();

        //4.输出部署的一些信息
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());
    }

}
