package com.ganilabs.interfaces;

public interface PluginMsgDispatcher {
    public void dispatch(String name , Object msg);
    public void addMessageQueue(MessageQueue msgQ);
}
