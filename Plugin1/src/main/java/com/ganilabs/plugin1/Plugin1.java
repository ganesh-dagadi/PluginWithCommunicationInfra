package com.ganilabs.plugin1;

import com.ganilabs.interfaces.MessageQueue;
import com.ganilabs.interfaces.PluginAPI;
import com.ganilabs.interfaces.PluginMsgDispatcher;
import com.ganilabs.interfaces.PluginMsgListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Plugin1 implements PluginAPI, PluginMsgListener , PluginMsgDispatcher {
    List<MessageQueue> listeners = new ArrayList<>();
    String scanRes = "empty";
    @Override
    public Integer getId() {
        return 1;
    }

    @Override
    public String getName() {
        return "plugin1";
    }

    @Override
    public void performAction() {
        System.out.println("From Plugin 1");
        System.out.println("scan res says : " + this.scanRes);
        System.out.println("---------------------");
    }

    @Override
    public List<String> getMessagesDispatched() {
        return List.of("noteContent");
    }

    @Override
    public List<String> getMessagesListenedTo() {
       return List.of("scanComplete");
    }

    @Override
    public void dispatch(String s, Object o) {
        for(MessageQueue listener : listeners){
            listener.messageReceived((String)o);
        }
    }

    @Override
    public void addMessageQueue(MessageQueue messageQueue) {
        this.listeners.add(messageQueue);
    }

    @Override
    public void messageReceived(String name, Object o) {
        if(Objects.equals(name, "scanComplete")) this.scanRes = (String) o;
        else System.out.println("Name of message unknown in Plugin1 listener");
    }
}
