package org.ganilabs.core;

import com.ganilabs.interfaces.PluginAPI;
import com.ganilabs.interfaces.PluginMsgDispatcher;
import com.ganilabs.interfaces.PluginMsgListener;

import java.util.*;

class App{
    private final List<String> messages = new ArrayList<>();
    Map<String , MessageQueueImpl> messageQueues = new HashMap<>();
    Map<String , PluginAPI> pluginsMap = new HashMap<>();
    public void run() {
        //Warning : Doing bad stuff here. performing hard TypeCasting. better way
        //          is to get dispatcher and listener from the PluginApi
        //initialize queues
        messages.add("scanComplete");
        messages.add("noteContent");
        for (String msgQName : messages) {
            messageQueues.put(msgQName, new MessageQueueImpl(msgQName));
        }
        //Loading plugins
        ServiceLoader<PluginAPI> loader = ServiceLoader.load(PluginAPI.class);
        for (PluginAPI plugin : loader) {
            pluginsMap.put(plugin.getName(), plugin);
        }

        //Populate queues
        Set<String> pluginNames = pluginsMap.keySet();
        for (String pluginName : pluginNames) {
            PluginAPI api = pluginsMap.get(pluginName);
            List<String> listensTo = api.getMessagesListenedTo();
            for (String listen : listensTo) {
                messageQueues.get(listen).addListener((PluginMsgListener) api);
            }

            List<String> dispatchesTo = api.getMessagesDispatched();
            for (String dispatch : dispatchesTo) {
                PluginMsgDispatcher pDisp = (PluginMsgDispatcher) api;
                pDisp.addMessageQueue(messageQueues.get(dispatch));
            }
        }

        PluginAPI plugin1 = pluginsMap.get("plugin1");
        PluginAPI plugin2 = pluginsMap.get("plugin2");
        PluginMsgDispatcher pl1D = (PluginMsgDispatcher)plugin1;
        pl1D.dispatch("noteContent" , "New Notes");
        plugin1.performAction();
        //Plugin 2 must have new Notes
        plugin2.performAction();
        PluginMsgDispatcher pld2 = (PluginMsgDispatcher) plugin2;
        pld2.dispatch("scanComplete" , "Some scan Data");
        //Plugin 1 must have Some Scan Data
        plugin1.performAction();
        plugin2.performAction();
    }


}
public class Core {
    //Assume coming from DB

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
