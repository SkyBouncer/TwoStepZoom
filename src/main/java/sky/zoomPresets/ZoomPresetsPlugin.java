package sky.zoomPresets;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ScriptID;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Slf4j
@PluginDescriptor(
		name = "Zoom presets"
)

public class ZoomPresetsPlugin extends Plugin implements KeyListener
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ZoomPresetsConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private KeyManager keyManager;

	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private int storedZoomValue;

	@Override
	protected void startUp() throws Exception
	{
		GetStoredZoom();
		keyManager.registerKeyListener(this);
	}

	@Override
	protected void shutDown() throws Exception
	{
		keyManager.unregisterKeyListener(this);
	}

	@Provides
	ZoomPresetsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ZoomPresetsConfig.class);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == config.zoomInKey().getKeyCode()) {
			zoomIn();
		} else if (e.getKeyCode() == config.zoomOutKey().getKeyCode()) {
			zoomOut();
		}
	}

	private void zoomIn() {
		OptionalInt nextZoomValue = GetNextZoomValue(true);
		if (nextZoomValue.isPresent()) applyZoom(nextZoomValue.getAsInt());
	}

	private void zoomOut() {
		OptionalInt nextZoomValue = GetNextZoomValue(false);
		if (nextZoomValue.isPresent()) applyZoom(nextZoomValue.getAsInt());
	}

	private OptionalInt GetNextZoomValue(boolean zoomIn)
	{
		IntStream presetStream = GetZoomPresets();

		if (zoomIn) return presetStream.filter(x -> x > storedZoomValue).min();
		return presetStream.filter(x -> x < storedZoomValue).max();
	}

	private IntStream GetZoomPresets()
	{
		String presetString = config.zoomPresets();

		if (presetString == null || presetString.trim().isEmpty()) return IntStream.empty();

		return Arrays.stream(presetString.split(","))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.mapToInt(s -> {
					try {
						return Integer.parseInt(s);
					} catch (NumberFormatException e) {
						return Integer.MIN_VALUE;
					}
				})
				.filter(v -> v != Integer.MIN_VALUE)
				.sorted();
	}

	private void applyZoom(int zoomValue) {
		clientThread.invokeLater(() -> client.runScript(ScriptID.CAMERA_DO_ZOOM, zoomValue, zoomValue));
		SetStoredZoom(zoomValue);
	}

	private void SetStoredZoom(int zoomValue)
	{
		storedZoomValue = zoomValue;
		configManager.setConfiguration(ZoomPresetsConfig.GROUP_NAME, ZoomPresetsConfig.STORED_ZOOM_VALUE_KEY, zoomValue);
	}

	private void GetStoredZoom()
	{
		try {
			storedZoomValue = Integer.parseInt(configManager.getConfiguration(ZoomPresetsConfig.GROUP_NAME, ZoomPresetsConfig.STORED_ZOOM_VALUE_KEY));
		} catch (NumberFormatException e) {
			storedZoomValue = 400;
		}
	}
}