package ru.stdian;

import ru.stdian.app.MainWindow;

import javax.swing.SwingUtilities;

class Main {

    public static void main(String[] args) {
		SwingUtilities.invokeLater(MainWindow::new);
    }
}
