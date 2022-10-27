package second;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;


/**
 *  设置多个审批人
 */
public class Hello4Note2ListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.addCandidateUser("panda-1");
        delegateTask.addCandidateUser("panda-2");

        String assignee = delegateTask.getAssignee();

        System.out.println(assignee);

    }
}
