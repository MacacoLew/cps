package net.begad666.bc.plugin.customprotocolsettings.features;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.begad666.bc.plugin.customprotocolsettings.utils.Config;
import net.begad666.bc.plugin.customprotocolsettings.utils.MainUtils;

public class ChangePingData implements Listener {

	
	@EventHandler
	public void Ping(ProxyPingEvent event) 
	{
		ServerPing serverPing = event.getResponse();
		if (Config.getconfig().getBoolean("settings.maintenance-enabled")) 
		{
			serverPing.setVersion(new ServerPing.Protocol("MAINTENANCE", 999));
			serverPing.setPlayers(new ServerPing.Players(0, 0, MainUtils.processmaintenancehovermessage()));
			serverPing.setDescriptionComponent(new TextComponent(MainUtils.replaceall(Config.getconfig().getString("motds.maintenance-motd.1") + "\n�r" + Config.getconfig().getString("motds.maintenance-motd.2"))));
		}
		else 
		{
			serverPing.setVersion(new ServerPing.Protocol(Config.getconfig().getString("network-info.name"), MainUtils.processversion(serverPing.getVersion().getProtocol())));
			serverPing.setPlayers(new ServerPing.Players(Config.getconfig().getInt("network-info.max-players"),ProxyServer.getInstance().getOnlineCount(),MainUtils.processhovermessage()));
			serverPing.setDescriptionComponent(new TextComponent(MainUtils.replaceall(Config.getconfig().getString("motds.default-motd.1") + "\n�r" + Config.getconfig().getString("motds.default-motd.2"))));
		}
		event.setResponse(serverPing); 
		serverPing = null;
	}


	
}
    
        



    


