package ru.stdian.app;

import javax.swing.*;

interface Window {
	void setLabel();
	void setButton();
	void setPanel();
	void setField();
	void setTable();
	default void setDecoration() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {}
	}
}
