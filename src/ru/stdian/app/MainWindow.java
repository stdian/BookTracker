package ru.stdian.app;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainWindow implements Window {
	public JFrame frame;
	public JPanel panel;
	public JTable table;
	public DefaultTableModel books;

	public MainWindow() {
		init();
	}

	public void getBooks() {
		books = new DefaultTableModel();
		books.setColumnCount(5);
		File file = new File("books");
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String book = sc.nextLine();
				String[] bookSplited = book.split(":");
				Object[] row = new Object[5];
				for (int i = 0; i <= 3; i++) {
					row[i] = bookSplited[i];
				}
				try {
					float readed = (Integer.parseInt(bookSplited[3]) * 1.0f)/(Integer.parseInt(bookSplited[2]) * 1.0f);
					row[4] = (int)(readed * 100) + "%";
				} catch (Exception ignored) {}
				books.addRow(row);
			}
			table.setModel(books);
		} catch (FileNotFoundException e) {
			try {
				FileWriter writer = new FileWriter("books");
				writer.write("");
				writer.flush();
				writer.close();
			} catch (IOException e1) {
				Notifications.showErrorNotification("Error", e1.toString());
			}
		}
	}

	@Override
	public void setLabel() {
		JLabel headerLabel1 = new JLabel("Name:");
		headerLabel1.setBounds(10, 40, 50, 20);
		headerLabel1.setFont(new Font("Arial", Font.BOLD, 15));

		JLabel headerLabel2 = new JLabel("Author:");
		headerLabel2.setBounds(170, 40, 100, 20);
		headerLabel2.setFont(new Font("Arial", Font.BOLD, 15));

		JLabel headerLabel3 = new JLabel("Pages:");
		headerLabel3.setBounds(320, 40, 100, 20);
		headerLabel3.setFont(new Font("Arial", Font.BOLD, 15));

		JLabel headerLabel4 = new JLabel("Read:");
		headerLabel4.setBounds(480, 40, 100, 20);
		headerLabel4.setFont(new Font("Arial", Font.BOLD, 15));

		panel.add(headerLabel1);
		panel.add(headerLabel2);
		panel.add(headerLabel3);
		panel.add(headerLabel4);
	}

	@Override
	public void setButton() {
		JButton addButton = new JButton("Add");
		addButton.setBounds(10, 10, 75, 25);
		addButton.addActionListener(e -> new EditWindow().init(-2, books, this));

		JButton editButton = new JButton("Edit");
		editButton.setBounds(90, 10, 75, 25);
		editButton.addActionListener(e -> {
			int row = table.getSelectedRow();
			getBooks();
			new EditWindow().init(row, books, this);
		});

		panel.add(addButton);
		panel.add(editButton);
	}

	@Override
	public void setPanel() {
		panel = new JPanel();
		panel.setLayout(null);
		frame.getContentPane().add(panel);
	}

	@Override
	public void setField() {

	}

	@Override
	public void setTable() {
		table = new JTable();
		table.setBounds(10, 60, 780, 500);
		table.setBorder(new LineBorder(Color.BLACK, 1));
		table.setSelectionMode(0);
		panel.add(table);
	}

	public void init() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setDecoration();
		frame.setSize(800, 600);
		frame.setResizable(false);
		setPanel();
		setLabel();
		setButton();
		setField();
		setTable();
		getBooks();
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
	}
}

