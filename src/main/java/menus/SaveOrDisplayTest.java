package menus;


/**

The SaveOrDisplay class represents a JFrame window that allows the user to choose whether to display
the schedule or save it to a file.
It contains two JButtons, one to display the schedule and one to save it. When the "Display" button
is clicked, the schedule is displayed using the MonthSchedule class. When the "Save" button is clicked,
a new ChooseExport window is created to allow the user to select a file location to save the schedule.
@author [Insert Your Name Here]
@version [Insert Version Number Here]
*/

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import WebSchedule.MonthSchedule;
import modules.ScheduleList;

class SaveOrDisplayTest extends JFrame {
ScheduleList list;
/**
 * Constructs a new SaveOrDisplay window with the given ScheduleList.
 * 
 * @param list the ScheduleList containing the schedules to be displayed or saved
 */
public SaveOrDisplayTest(final ScheduleList list) {
	super("Save or Display");
	this.list = list;
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLayout(new FlowLayout());

	// Create buttons
	JButton displayButton = new JButton("Display");
	displayButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			MonthSchedule.initializeSchdule(list.getSchedules());
		}
	});
	JButton saveButton = new JButton("Save");
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
}