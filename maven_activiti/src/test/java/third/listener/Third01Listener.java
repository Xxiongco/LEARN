package third.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class Third01Listener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {

        System.out.println("task name : " + delegateTask.getName());
        System.out.println("task id : " + delegateTask.getId());
        System.out.println("task assignee : " + delegateTask.getAssignee());
        System.out.println("task variable : " + delegateTask.getVariables());
        System.out.println("task instance : " + delegateTask.getProcessInstanceId());
        System.out.println("task execution id : " + delegateTask.getExecutionId());
        System.out.println("task candidates  : " + delegateTask.getCandidates());

        System.out.println(delegateTask);
    }
}
