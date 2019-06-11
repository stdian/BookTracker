package ru.stdian.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EditWindow implements Window {
	JFrame frame = new JFrame();
	JPanel panel;
	DefaultTableModel books;
	SpinnerModel spinnerModelSize;
	SpinnerModel spinnerModelRead;
	JSpinner sizeSpinner, readSpinner;
	JTextField nameField, authorField;

	int row;
	MainWindow mainFrame;


	@Override
	public void setLabel() {
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(10, 50, 100, 30);

		JLabel authorLabel = new JLabel("Author:");
		authorLabel.setBounds(10, 80, 100, 30);

		JLabel sizeLabel = new JLabel("Size:");
		sizeLabel.setBounds(10, 110, 100, 30);

		JLabel readLabel = new JLabel("Read:");
		readLabel.setBounds(10, 140, 100, 30);

		panel.add(nameLabel);
		panel.add(authorLabel);
		panel.add(sizeLabel);
		panel.add(readLabel);
	}

	@Override
	public void setButton() {
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(10, 10, 75, 25);
		saveButton.addActionListener(e -> save());

		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(90, 10, 75, 25);

		panel.add(saveButton);
		panel.add(deleteButton);
	}

	@Override
	public void setPanel() {
		panel = new JPanel();
		panel.setLayout(null);
		frame.getContentPane().add(panel);
	}

	@Override
	public void setField() {
		nameField = new JTextField();
		nameField.setBounds(100, 50, 190, 30);

		authorField = new JTextField();
		authorField.setBounds(100, 80, 190, 30);

		sizeSpinner = new JSpinner();
		sizeSpinner.setBounds(100, 110, 70, 30);

		readSpinner = new JSpinner();
		readSpinner.setBounds(100, 140, 70, 30);

		panel.add(nameField);
		panel.add(authorField);
		panel.add(sizeSpinner);
		panel.add(readSpinner);
	}

	@Override
	public void setTable() {

	}

	public void newBook() {
		spinnerModelSize = new SpinnerNumberModel(1, 1, 9999, 1);
		spinnerModelRead = new SpinnerNumberModel(1, 1, 9999, 1);
		sizeSpinner.setModel(spinnerModelSize);
		readSpinner.setModel(spinnerModelRead);
	}

	public void editBook() {
		String name = String.valueOf(books.getValueAt(row, 0));
		String author = String.valueOf(books.getValueAt(row, 1));
		nameField.setText(name);
		authorField.setText(author);

		int size = Integer.parseInt(String.valueOf(books.getValueAt(row, 2)));
		int read = Integer.parseInt(String.valueOf(books.getValueAt(row, 3)));
		spinnerModelSize = new SpinnerNumberModel(size, 1, 9999, 1);
		spinnerModelRead = new SpinnerNumberModel(read, 1, size, 1);
		sizeSpinner.setModel(spinnerModelSize);
		readSpinner.setModel(spinnerModelRead);
	}

	public void save() {
		String name = nameField.getText().trim();
		String author = authorField.getText().trim();
		int size = (int) sizeSpinner.getValue();
		int read = (int) readSpinner.getValue();
		if (read > size) {
			read = 1;
		}
		if (!name.isEmpty() && !author.isEmpty()) {
			if (row > -1) {
				String bookString = books.getValueAt(row, 0) + ":" + books.getValueAt(row, 1) + ":"
						+ books.getValueAt(row, 2) + ":" + books.getValueAt(row, 3);
				ArrayList<String> newFile = new ArrayList<>();
				File file = new File("books");
				try {
					Scanner sc = new Scanner(file);
					while (sc.hasNextLine()) {
						newFile.add(sc.nextLine());
					}
				} catch (Exception ignored) {
				}
				int index = newFile.indexOf(bookString);
				newFile.remove(index);
				newFile.add(index, name + ":" + author + ":" + size + ":" + read);
				try {
					FileWriter writer = new FileWriter("books");
					for (String book : newFile) {
						writer.write(book + "\n");
					}
					writer.flush();
					writer.close();
					Notifications.showInfoNotification("Info", "Book saved successfully!");
					frame.dispose();
					mainFrame.getBooks();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter("books", true));
					out.write(name + ":" + author + ":" + size + ":" + read + "\n");
					out.close();
					Notifications.showInfoNotification("Info", "Book saved successfully!");
					frame.dispose();
					mainFrame.getBooks();
				}
				catch (IOException e) {
					Notifications.showErrorNotification("Error", e.toString());
				}
			}
		} else {
			Notifications.showErrorNotification("Error", "Some fields are empty!");
		}
	}

	public void init(int row, DefaultTableModel books, MainWindow frame) {
		this.books = books;
		this.row = row;
		this.mainFrame = frame;
		setDecoration();
		this.frame.setResizable(false);
		this.frame.setSize(300, 200);
		setPanel();
		setLabel();
		setField();
		setButton();
		if (row == -2) {
			newBook();
			this.frame.setVisible(true);
		} else if (row > -1) {
			editBook();
			this.frame.setVisible(true);
		}
	}
}
