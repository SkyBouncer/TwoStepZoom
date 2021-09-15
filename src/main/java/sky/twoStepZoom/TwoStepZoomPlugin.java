package sky.twoStepZoom;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.awt.event.KeyEvent;

@PluginDescriptor(
		name = "Two step zoom",
		description = "Adjusts the zoom between two levels",
		tags = {"zoom"}
)

public class TwoStepZoomPlugin extends Plugin implements KeyListener
{
	boolean standardDown = false;
	boolean zoomedDown = false;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private TwoStepZoomConfig config;

	@Inject
	private KeyManager keyManager;

	@Provides
	TwoStepZoomConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TwoStepZoomConfig.class);
	}

	@Override
	protected void startUp() {
		keyManager.registerKeyListener(this);
	}

	@Override
	protected void shutDown() {
		keyManager.unregisterKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == config.standardZoomKey().getKeyCode()) {
			standardDown = true;
		} else if (e.getKeyCode() == config.zoomedZoomKey().getKeyCode()) {
			zoomedDown = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int zoomValue;
		if (e.getKeyCode() == config.standardZoomKey().getKeyCode()) {
			standardDown = false;
			zoomValue = config.standardZoomValue();
			clientThread.invokeLater(() -> client.runScript(ScriptID.CAMERA_DO_ZOOM, zoomValue, zoomValue));
		} else if (e.getKeyCode() == config.zoomedZoomKey().getKeyCode()) {
			zoomedDown = false;
			zoomValue = config.zoomedZoomValue();
			clientThread.invokeLater(() -> client.runScript(ScriptID.CAMERA_DO_ZOOM, zoomValue, zoomValue));
		}
	}
}
