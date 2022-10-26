package com.service;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class Hello3ListenerImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("hahahahahahhahahaha");
        delegateTask.setAssignee("ding");
    }
}
