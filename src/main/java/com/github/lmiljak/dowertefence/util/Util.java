package com.github.lmiljak.dowertefence.util;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

/**
 * Utility class containing some useful static methods.
 */
public class Util {

	/**
	 * Throws an event, notifying all listeners of this type of event.
	 * 
	 * @param event
	 *            The event to throw.
	 */
	public void throwEvent(Event event) {
		Bukkit.getServer().getPluginManager().callEvent(event);
	}

}
