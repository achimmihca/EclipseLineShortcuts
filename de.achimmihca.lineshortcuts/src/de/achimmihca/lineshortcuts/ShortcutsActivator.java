package de.achimmihca.lineshortcuts;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ShortcutsActivator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "de.achimmihca.lineshortcuts";

	private static ShortcutsActivator plugin;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start( context );
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop( context );
	}

	public static ShortcutsActivator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin( PLUGIN_ID, path );
	}
}
