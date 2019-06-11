package ru.stdian.app;

import java.awt.*;

public class Notifications {

	public static void showNotification(String caption, String text, TrayIcon.MessageType type) {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
			TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
			trayIcon.setImageAutoSize(true);
			trayIcon.setToolTip("System tray icon demo");
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			trayIcon.displayMessage(caption, text, type);
		}
	}

	public static void showErrorNotification(String caption, String text) {
		showNotification(caption, text, TrayIcon.MessageType.ERROR);
	}

	public static void showInfoNotification(String caption, String text) {
		showNotification(caption, text, TrayIcon.MessageType.INFO);
	}

	public static void showWarningNotification(String caption, String text) {
		showNotification(caption, text, TrayIcon.MessageType.WARNING);
	}

}
