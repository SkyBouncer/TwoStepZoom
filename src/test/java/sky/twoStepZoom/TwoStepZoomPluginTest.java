package sky.twoStepZoom;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TwoStepZoomPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(TwoStepZoomPlugin.class);
		RuneLite.main(args);
	}
}