package second;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;


/**
 *  设置多个审批人
 */
public class Hello4Note3ListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println(delegateTask.getAssignee());
        System.out.println(delegateTask);
    }
}
