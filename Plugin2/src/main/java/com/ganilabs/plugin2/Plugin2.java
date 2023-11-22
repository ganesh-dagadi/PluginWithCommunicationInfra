package com.ganilabs.plugin2;

import com.ganilabs.interfaces.MessageQueue;
import com.ganilabs.interfaces.PluginAPI;
import com.ganilabs.interfaces.PluginMsgDispatcher;
import com.ganilabs.interfaces.PluginMsgListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Plugin2 implements PluginAPI, PluginMsgListener , PluginMsgDispatcher {
    String noteContent = "Empty";
    List<MessageQueue> listeners = new ArrayList<>();
    @Override
    public Integer getId() {
        return 2;
    }

    @Override
    public String getName() {
        return "plugin2";
    }

    @Override
    public void performAction() {
        System.out.println("From Plugin 2");
        System.out.println("noteContent says : " + this.noteContent);
        System.out.println("---------------------");
    }

    @Override
    public List<String> getMessagesDispatched() {
        return List.of("scanComplete");
    }

    @Override
    public List<String> getMessagesListenedTo() {
        return List.of("noteContent");
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
        if(Objects.equals(name, "noteContent")) this.noteContent =(String) o;
        else System.out.println("Name of message unknown in Plugin2 listener");
    }
}
