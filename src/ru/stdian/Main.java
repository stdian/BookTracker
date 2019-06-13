package ru.stdian;

import ru.stdian.app.MainWindow;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
		SwingUtilities.invokeLater(MainWindow::new);
    }
}
