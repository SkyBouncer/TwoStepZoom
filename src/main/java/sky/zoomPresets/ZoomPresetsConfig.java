package sky.zoomPresets;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ModifierlessKeybind;
import java.awt.event.KeyEvent;

@ConfigGroup("zoom")
public interface ZoomPresetsConfig extends Config
{
	String GROUP_NAME = "ZoomPresets";
	String STORED_ZOOM_VALUE_KEY = "zoomValue";

	@ConfigItem(
			keyName = "Presets",
			name = "Zoom presets",
			description = "Provide the zoom presets (numbers between 0 - 896) as a comma separated list.",
			position = 1
	)

	default String zoomPresets()
	{
		return "400, 700";
	}

	@ConfigItem(
			position = 2,
			keyName = "zoomIn",
			name = "Zoom in key",
			description = "The key to zoom in."
	)

	default ModifierlessKeybind zoomInKey()
	{
		return new ModifierlessKeybind(KeyEvent.VK_PAGE_UP, 0);
	}

	@ConfigItem(
			position = 3,
			keyName = "zoomOut",
			name = "Zoom out key",
			description = "The key to zoom out."
	)
	default ModifierlessKeybind zoomOutKey()
	{
		return new ModifierlessKeybind(KeyEvent.VK_PAGE_DOWN, 0);
	}
}
