package menus;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import modules.ScheduleList;
import utils.SaveFile;



public class ChooseExport extends JFrame {
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;
	private ScheduleList ss;
	public ChooseExport(final ScheduleList ss) {
		super("Export File");
		this.ss = ss;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		// Create text field
		radioButton1 = new JRadioButton("Export Json");
		radioButton2 = new JRadioButton("Export Csv");

		// Create button group so only one radio button can be selected
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButton1);
		buttonGroup.add(radioButton2);
		// Create submit button
		JButton exportButton = new JButton("Export");
		// Create button
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = FileChooser.saveFile();
				if(radioButton1.isSelected()) {
					SaveFile.saveFileJson(ss, path);
				}else {
					SaveFile.saveFileCsv(ss, path);
				}
			}
		});

		// Add components to window
		add(radioButton1);
		add(radioButton2);
		add(exportButton);

		// Set window properties
		setSize(300, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
