package com.ganilabs.interfaces;

import java.util.List;

public interface PluginAPI {
    public Integer getId();
    public String getName();
    public void performAction();
    public List<String> getMessagesDispatched();
    public List<String> getMessagesListenedTo();
}
