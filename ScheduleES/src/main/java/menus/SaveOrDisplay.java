package menus;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import WebSchedule.MonthSchedule;
import modules.ScheduleList;

class SaveOrDisplay extends JFrame {
	ScheduleList list;

	public SaveOrDisplay(final ScheduleList list) {
		super("Save or Display");
		this.list = list;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		// Create text field

		// Create button
		JButton displayButton = new JButton("Display");
		displayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MonthSchedule.initializeSchdule(list.getSchedules());
			}
		});
		JButton saveButton = new JButton("save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new ChooseExport(list);
			}
		});

		// Add components to window
		add(displayButton);
		add(saveButton);

		// Set window properties
		setSize(300, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	

	public static void main(String[] args) {
		new ImportCalendarWebCall();
	}

}