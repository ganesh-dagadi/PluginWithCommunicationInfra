package com.ganilabs.interfaces;

public interface MessageQueue {
    public String getName();
    public void dispatch(String data);
    public void messageReceived(String data);
    public void addListener(PluginMsgListener listener);
}
