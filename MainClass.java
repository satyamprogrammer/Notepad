import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.undo.UndoManager;

public class MainClass {

	private JTextArea textarea;
	private JFrame frame;
	private JMenuBar menubar;
	private JScrollPane scroll;
	private JDialog dialog;
	private JCheckBoxMenuItem statusBar;
	private JTextField status;

	public static void main(String[] args) {
		MainClass mc = new MainClass();
		mc.go();
	}

	private void go() {
		frame = new JFrame("Notepad");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textarea = new JTextArea(20, 40);
		// textarea.setLineWrap(true);
		scroll = new JScrollPane(textarea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		menubar = new JMenuBar();
		JMenu file, edit, format, view, help;
		file = new JMenu("File");
		edit = new JMenu("Edit");
		format = new JMenu("Format");
		view = new JMenu("View");
		help = new JMenu("Help");

		// File--------------------------
		JMenuItem New, Open, Save, SaveAs, PageSetup, Print, Exit;
		New = new JMenuItem("New");
		New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		New.addActionListener(new NewActionListener());
		Open = new JMenuItem("Open...");
		Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		Open.addActionListener(new OpenActionListener());
		Save = new JMenuItem("Save");
		Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		Save.addActionListener(new SaveActionListener());
		SaveAs = new JMenuItem("Save As...");
		SaveAs.addActionListener(new SaveActionListener());
		PageSetup = new JMenuItem("Page Setup...");
		Print = new JMenuItem("Print...");
		Print.addActionListener(new PrintActionListener());
		Print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		Exit = new JMenuItem("Exit");
		Exit.addActionListener(new ExitActionListener());
		Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

		file.add(New);
		file.add(Open);
		file.add(Save);
		// file.add(SaveAs);
		file.addSeparator();
		// file.add(PageSetup);
		file.add(Print);
		file.addSeparator();
		file.add(Exit);

		// Edit---------------------
		JMenuItem undo, cut, copy, paste, delete, find, findNext, replace, Goto, selectall, time, redo;
		undo = new JMenuItem("Undo");
		UndoManager manager = new UndoManager();
		textarea.getDocument().addUndoableEditListener(manager);
		undo.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.undo();
			}
		});
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		redo = new JMenuItem("Redo");
		redo.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.redo();
			}
		});
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		cut = new JMenuItem("Cut");
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		delete = new JMenuItem("Delete");
		find = new JMenuItem("Find...");
		find.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		findNext = new JMenuItem("Find Next");
		replace = new JMenuItem("Replace");
		Goto = new JMenuItem("Go To...");
		selectall = new JMenuItem("Select All");
		time = new JMenuItem("Time/Date");
		time.addActionListener(new TimeActionListener());

		edit.add(undo);
		edit.add(redo);
		edit.addSeparator();
		// edit.add(cut);
		// edit.add(copy);
		// edit.add(paste);
		// edit.add(delete);
		edit.addSeparator();
		edit.add(find);
		// edit.add(findNext);
		edit.add(replace);
		edit.add(Goto);
		edit.addSeparator();
		//edit.add(selectall);
		edit.add(time);

		// Format----------------------
		JMenuItem font;
		JCheckBoxMenuItem wordwrap = new JCheckBoxMenuItem("Wordwrap");
		wordwrap.addItemListener(new WordWrapActionListener());
		font = new JMenuItem("Font");
		font.addActionListener(new FontActionListener());

		format.add(wordwrap);
		format.add(font);

		// View----------------------
		 statusBar = new JCheckBoxMenuItem("Status Bar");
		statusBar.addItemListener(new StatusActionListener());
		view.add(statusBar);

		// Help---------------------
		JMenuItem viewHelp, about;
		viewHelp = new JMenuItem("View Help");
		viewHelp.addActionListener(new ActionListener() {
			JDialog dl;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				dl = new JDialog(frame, "Help");
				dl.setSize(400, 100);
				JLabel label = new JLabel();
				label.setText("Email on satyam.g16@gmail.com");
				label.setVisible(true);
				dl.add(label);
				dl.setVisible(true);
			}
		});
		about = new JMenuItem("About NotePad");
		about.addActionListener(new ActionListener() {
			JDialog dl;

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});
		help.add(viewHelp);
		help.addSeparator();
		help.add(about);

		menubar.add(file);
		menubar.add(edit);
		menubar.add(format);
		menubar.add(view);
		menubar.add(help);

		frame.setJMenuBar(menubar);
		frame.getContentPane().add(scroll, BorderLayout.CENTER);
        frame. addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	//Code needed to be Written
                
            }
        });
		frame.setSize(500, 500);
		frame.setVisible(true);
	}

	// File Menu Items ActionListeners
	// ==========================================================================================
	// New MenuItem---------------------
	private class NewActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			MainClass newins = new MainClass();
			// frame.setDefaultCloseOperation(JFrame.);
			newins.go();
		}
	}

	// Open MenuItem--------------------
	private class OpenActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser fc;
			fc = new JFileChooser(FileSystemView.getFileSystemView());
			fc.showOpenDialog(frame);
			fileOpen(fc.getSelectedFile());
		}
	}

	private void fileOpen(File selectedFile) {

		String S = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
			while ((S = reader.readLine()) != null) {
				textarea.append(S);
				textarea.append("\n");
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
	}

	// Save ActionListener---------------------------
	private class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView());
			fc.showSaveDialog(frame);
			FileSave(fc.getSelectedFile());
		}
	}

	private void FileSave(File selectedFile) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile));
			bw.write(textarea.getText());
			bw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// Print Listener
	private class PrintActionListener implements ActionListener, Printable {
		JFrame frameToPrint;

		@Override
		public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
			if (page > 0)
				return NO_SUCH_PAGE;
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(pf.getImageableX(), pf.getImageableY());

			frame = new JFrame("Print");
			frameToPrint.printAll(g);

			return PAGE_EXISTS;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(this);
			boolean ok = job.printDialog();
			if (ok) {
				try {
					job.print();
				} catch (PrinterException ex) {
					/* The job did not successfully complete */
				}
			}

		}

	}
	// Exit Listener

	private class ExitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (textarea.getText() != "") {
				dialog = new JDialog(frame, "Notepad", true);
				dialog.setLayout(new FlowLayout());
				JButton save, dontSave, Cancel;
				save = new JButton("Save");
				dontSave = new JButton("Don't Save");
				Cancel = new JButton("Cancel");
				dialog.add(save);
				save.addActionListener(new SaveActionListener());
				dialog.add(dontSave);
				dontSave.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
						dialog.dispose();
						frame.setVisible(false);
						frame.dispose();
					}
				});
				dialog.add(Cancel);
				Cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						dialog.setVisible(false);
						dialog.dispose();
					}
				});
				dialog.setLocation(100, 100);
				dialog.setSize(300, 100);
				dialog.setVisible(true);
			} else {
				frame.setVisible(false);
				frame.dispose();
			}
		}

	}

	// Edit Menu Items Listener
	// ==================================================

	// Time/Date Listener
	private class TimeActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			textarea.append(dateFormat.format(date));

		}

	}

	// Format Menu Item Listener
	// =========================================================
	// wordwrap Listener
	private class WordWrapActionListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {

			if (event.getStateChange() == 1) {
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				textarea.setLineWrap(true);
				statusBar.setVisible(false);
				status.setVisible(false);
			} else {
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				textarea.setLineWrap(false);
				statusBar.setVisible(true);
				status.setVisible(true);

			}
		}
	}

	// font listener---------------------------------------------------
	private class FontActionListener implements ActionListener {
		private JDialog fontDialog;
		private String[] differentFonts;
		private String[] fontStyle = { "PLAIN", "BOLD", "ITALIC" };
		private String[] Size = { "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36",
				"48", "72" };
		private Font fonts;
		private JComboBox comboFont, FontStyle, comboSize;
		private JLabel label;
		private JButton apply;

		@Override
		public void actionPerformed(ActionEvent arg0) {

			fontDialog = new JDialog(frame, "Font", true);
			fontDialog.setLayout(new FlowLayout());

			label = new JLabel("Satyam Gupta");
			label.setSize(200, 200);

			differentFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
			comboFont = new JComboBox(differentFonts);
			comboFont.addActionListener(new comboActionListener());
			FontStyle = new JComboBox(fontStyle);
			FontStyle.addActionListener(new comboActionListener());
			comboSize = new JComboBox(Size);
			comboSize.addActionListener(new comboActionListener());
			apply = new JButton("Apply Changes");
			apply.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					textarea.setFont(fonts);
					fontDialog.setVisible(false);
					fontDialog.dispose();

				}

			});

			fontDialog.add(comboFont);
			fontDialog.add(FontStyle);
			fontDialog.add(comboSize);
			fontDialog.add(label);
			fontDialog.add(apply);
			fontDialog.setSize(400, 400);
			fontDialog.setVisible(true);

		}

		private class comboActionListener implements ActionListener {
			int s;
			String data, data1;

			@Override

			public void actionPerformed(ActionEvent arg0) {
				data = (String) comboFont.getItemAt(comboFont.getSelectedIndex());
				data1 = (String) FontStyle.getItemAt(FontStyle.getSelectedIndex());
				s = Integer.parseInt((String) comboSize.getItemAt(comboSize.getSelectedIndex()));
				fonts = new Font(data, 2, s);
				label.setFont(fonts);
			}
		}
	}

	// View Menu items Listener
	// ====================================
	// StatusBar

	private class StatusActionListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == 1) {
				textarea.addCaretListener(new CaretListener() {
					public void caretUpdate(CaretEvent e) {
						// JTextArea editArea
						textarea = (JTextArea) e.getSource();
						int linenum = 1;
						int columnnum = 1;
						try {

							int caretpos = textarea.getCaretPosition();

							linenum = textarea.getLineOfOffset(caretpos);
							columnnum = caretpos - textarea.getLineStartOffset(linenum);

							linenum += 1;
						} catch (Exception ex) {
						}

						updateStatus(linenum, columnnum);
					}
				});
				status = new JTextField();
				status.setVisible(true);
				frame.getContentPane().add(status, BorderLayout.SOUTH);

				// Give the status update value
				updateStatus(1, 1);
			}
		}

		private void updateStatus(int linenumber, int columnnumber) {
			status.setText("Line: " + linenumber + " Column: " + columnnumber);
		}

	}
}
