package ru.stdian.app;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainWindow implements Window {
	JFrame frame;
	private JPanel panel;
	private JTable table;
	private DefaultTableModel books;
	private DefaultTableCellRenderer centerRenderer;

	private final Font HEADER_FONT = new Font("Arial", Font.BOLD, 18);

	public MainWindow() {
		init();
	}

	void getBooks() {
		books = new DefaultTableModel();
		books.setColumnCount(5);
		File file = new File("books");
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String[] bookSplited = sc.nextLine().split(":");
				Object[] row = new Object[5];
				System.arraycopy(bookSplited, 0, row, 0, 4);
				try {
					float progress = (Integer.parseInt(bookSplited[3]) * 1.0f) / (Integer.parseInt(bookSplited[2]) * 1.0f);
					row[4] = (int) (progress * 100) + "%";
				} catch (NumberFormatException ignored) {
					row[4] = "";
				}
				books.addRow(row);
			}
			table.setModel(books);
			int[] sizes;
			if (books.getRowCount() > 16) {
				table.setBounds(10, 60, 780, 500);
				sizes = new int[]{250, 250, 80, 80, 120};
			} else {
				table.setBounds(10, 60, 800, 500);
				sizes = new int[]{250, 250, 80, 80, 135};
			}
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			for (int i = 0; i < 5; i++) {
				table.getColumnModel().getColumn(i).setPreferredWidth(sizes[i]);
				table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		} catch (FileNotFoundException e) {
			createNewBooksFile();
		}
	}

	private void createNewBooksFile() {
		try {
			FileWriter writer = new FileWriter("books");
			writer.write("");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Notifications.showErrorNotification("Error", e.toString());
		}
	}

	@Override
	public void setLabel() {
		JLabel headerLabel1 = new JLabel("Name:");
		headerLabel1.setBounds(10, 40, 100, 20);
		headerLabel1.setFont(HEADER_FONT);

		JLabel headerLabel2 = new JLabel("Author:");
		headerLabel2.setBounds(260, 40, 100, 20);
		headerLabel2.setFont(HEADER_FONT);

		JLabel headerLabel3 = new JLabel("Pages:");
		headerLabel3.setBounds(510, 40, 100, 20);
		headerLabel3.setFont(HEADER_FONT);

		JLabel headerLabel4 = new JLabel("Read:");
		headerLabel4.setBounds(590, 40, 100, 20);
		headerLabel4.setFont(HEADER_FONT);

		JLabel headerLabel5 = new JLabel("Progress:");
		headerLabel5.setBounds(670, 40, 100, 20);
		headerLabel5.setFont(HEADER_FONT);

		panel.add(headerLabel1);
		panel.add(headerLabel2);
		panel.add(headerLabel3);
		panel.add(headerLabel4);
		panel.add(headerLabel5);
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
	public void setField() {}

	@Override
	public void setTable() {
		table = new JTable();
		table.setBounds(10, 60, 780, 500);
		table.setBorder(new LineBorder(Color.BLACK, 1));
		table.setSelectionMode(0);
		table.setDefaultEditor(Object.class, null);
		table.setGridColor(Color.LIGHT_GRAY);
		table.setRowHeight(30);
		table.setDragEnabled(false);
		table.setTableHeader(null);

		centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);

		JScrollPane scrollPane = new JScrollPane(table);
/*
		scrollPane.add(table);
		scrollPane.setPreferredSize(new Dimension(780, 500));
		scrollPane.setSize(new Dimension(780, 500));
*/
		scrollPane.setBounds(10, 60, 800, 500);
		panel.add(scrollPane);
	}

	private void init() {
		frame = new JFrame();
		setDecoration();

		if (OsUtils.isWindows()) frame.setSize(840, 610);
		else frame.setSize(820, 600);

		setPanel();
		setLabel();
		setButton();
		setField();
		setTable();
		getBooks();

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
	}
}
