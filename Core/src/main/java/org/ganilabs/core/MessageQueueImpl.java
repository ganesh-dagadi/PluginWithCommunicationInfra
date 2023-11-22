package org.ganilabs.core;

import com.ganilabs.interfaces.MessageQueue;
import com.ganilabs.interfaces.PluginMsgListener;

import java.util.ArrayList;
import java.util.List;

public class MessageQueueImpl implements MessageQueue {
    List<PluginMsgListener> listeners = new ArrayList<>();
    private String name;
    MessageQueueImpl(String name){
      this.name = name;
    };
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void dispatch(String data) {
        for(PluginMsgListener listener : this.listeners){
            listener.messageReceived( this.name , data);
        }
    }

    @Override
    public void messageReceived(String data) {
        this.dispatch(data);
    }
    @Override
    public void addListener(PluginMsgListener listener){
        this.listeners.add(listener);
    }
}
