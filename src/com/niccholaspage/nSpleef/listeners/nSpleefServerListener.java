package com.niccholaspage.nSpleef.listeners;

import com.niccholaspage.nSpleef.nSpleef;
import com.nijikokun.register.payment.Methods;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

public class nSpleefServerListener extends ServerListener {
    private nSpleef plugin;
    private Methods Methods = null;

    public nSpleefServerListener(nSpleef plugin) {
        this.plugin = plugin;
        Methods = new Methods();
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        if (Methods != null && Methods.hasMethod()) {
            Boolean check = Methods.checkDisabled(event.getPlugin());
            if(check) {
                plugin.method = null;
                System.out.println("[nSpleef] Economy plugin disabled.");
            }
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        if (!Methods.hasMethod()) {
            if(Methods.setMethod(event.getPlugin())) {
                plugin.method = Methods.getMethod();
                System.out.println("[nSpleef] Economy plugin found (" + plugin.method.getName() + " version: " + plugin.method.getVersion() + ")");
            }
        }
    }
}