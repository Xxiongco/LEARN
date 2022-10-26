package second;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;


/**
 * 注意事项，使用自带的画图软件，是没有办法设置任务监听器的，只有执行监听器。
 */
public class Hello3ListenerImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("ding");
    }
}
