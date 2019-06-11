package ru.stdian.app;

import javax.swing.*;

public interface Window {
	public void setLabel();
	public void setButton();
	public void setPanel();
	public void setField();
	public void setTable();
	public default void setDecoration() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {}
	}
}
