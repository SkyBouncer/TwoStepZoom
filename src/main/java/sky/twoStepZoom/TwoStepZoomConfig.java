package sky.twoStepZoom;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;
import net.runelite.client.config.ModifierlessKeybind;
import java.awt.event.KeyEvent;

@ConfigGroup("zoom")
public interface TwoStepZoomConfig extends Config
{
	@ConfigItem(
			keyName = "StandardZoomValue",
			name = "Standard zoom position",
			description = "Position of standard zoom",
			position = 1
	)
	@Range(
			min = 0,
			max = 896
	)
	default int standardZoomValue()
	{
		return 432;
	}

	@ConfigItem(
			keyName = "ZoomedZoomValue",
			name = "Zoomed zoom position",
			description = "Position of zoomed zoom",
			position = 2
	)
	@Range(
			min = 0,
			max = 896
	)
	default int zoomedZoomValue()
	{
		return 700;
	}

	@ConfigItem(
			position = 3,
			keyName = "standardZoom",
			name = "Standard zoom key",
			description = "The key to return to standard zoom."
	)
	default ModifierlessKeybind standardZoomKey()
	{
		return new ModifierlessKeybind(KeyEvent.VK_PAGE_DOWN, 0);
	}

	@ConfigItem(
			position = 4,
			keyName = "zoomedZoom",
			name = "Zoomed zoom key",
			description = "The key to return to zoomed zoom."
	)
	default ModifierlessKeybind zoomedZoomKey()
	{
		return new ModifierlessKeybind(KeyEvent.VK_PAGE_UP, 0);
	}
}
