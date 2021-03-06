package begad.mc.bc.plugin.cps.features;

import begad.mc.bc.plugin.cps.Core;
import begad.mc.bc.plugin.cps.utils.Utils;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChangePingData implements Listener {
    public ChangePingData() {
    }

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        if (event.getResponse() == null || event instanceof Cancellable && ((Cancellable) event).isCancelled()) {
			return;
		}
        
        ServerPing serverPing = event.getResponse();
        if (Core.getConfig().get().getBoolean("settings.maintenance-enabled")) {
            serverPing.setVersion(new Protocol(Core.getConfig().get().getString("network-info.maintenance-version"), 999));
            serverPing.setPlayers(new Players(0, 0, Core.getConfig().get().getBoolean("hover-messages.enable") ? Utils.getMaintenanceHoverMessage() : null));
            serverPing.setDescriptionComponent(new TextComponent(Utils.replaceEveryThing(Core.getConfig().get().getString("motds.maintenance-motd.1") + "\n" + Core.getConfig().get().getString("motds.maintenance-motd.2"))));
        } else {
            serverPing.setVersion(new Protocol(Utils.replaceEveryThing(Core.getConfig().get().getString("network-info.server-version")), Utils.getVersion(serverPing.getVersion().getProtocol())));
            serverPing.setPlayers(new Players(Core.getConfig().get().getInt("network-info.max-players") > 0 ? Core.getConfig().get().getInt("network-info.max-players") : (Core.redisBungeeIntegration.getPlayerCount() - (Core.getConfig().get().getBoolean("settings.vanish-ping-reduce") ? Core.vanishManager.getPlayers().size() : 0)) + 1, Core.redisBungeeIntegration.getPlayerCount() - (Core.getConfig().get().getBoolean("settings.vanish-ping-reduce") ? Core.vanishManager.getPlayers().size() : 0), Core.getConfig().get().getBoolean("hover-messages.enable") ? Utils.getHoverMessage() : null));
            serverPing.setDescriptionComponent(new TextComponent(Utils.replaceEveryThing(Core.getConfig().get().getString("motds.default-motd.1") + "\n" + Core.getConfig().get().getString("motds.default-motd.2"))));
        }

        event.setResponse(serverPing);
    }
}
