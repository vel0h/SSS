package tehtros.bukkit.SSS;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.bukkit.plugin.java.JavaPlugin;

import tehtros.bukkit.TCastAPI.TCastAPI;

public class SSS extends JavaPlugin {
	TCastAPI api = new TCastAPI(this);
	
	@Override
	public void onEnable() {
		startChecks();
	}

	private void startChecks() {
		this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run() {
				try {
					URL url = new URL("http://session.minecraft.net");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.connect();
					if(connection.getResponseCode() == 503) {
						api.tcast("§cWARNING! The Minecraft Session Servers are DOWN!");
						api.tcast("§cIf you leave, you CANNOT join back!");
						String[] splits = connection.getResponseMessage().split(":");
						api.tcast("§cReason:" + splits[1]);
					}
					connection.disconnect();
				} catch(MalformedURLException e) {
					e.printStackTrace();
				} catch(ProtocolException e) {
					e.printStackTrace();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		},200L, 12000);
	}
}
