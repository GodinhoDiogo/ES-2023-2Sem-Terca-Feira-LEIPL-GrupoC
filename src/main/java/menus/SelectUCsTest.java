package menus;

import javax.swing.*;

import modules.Schedule;
import modules.ScheduleList;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectUCsTest extends JFrame {
	// Instance variables
	private List<JCheckBox> optionCheckBoxes;
	private JButton submitButton;
	private ScheduleList list;
	List<String> selectedOptions2 = new ArrayList<String>();

	/**
	 * Constructs a SelectUCs menu with the given schedule list and options.
	 *
	 * @param sl the schedule list to be filtered
	 * @param options the list of options to be displayed as checkboxes
	 * @throws IOException if there is an error in reading the file
	 */
	public SelectUCsTest(ScheduleList sl, List<String> options) throws IOException {
		
		// Set up the menu panel
		super("Menu Swing");
		list = sl;
		JPanel optionsPanel = new JPanel(new GridLayout(options.size() + 1, 1));
		optionCheckBoxes = new ArrayList<JCheckBox>();
		final JCheckBox selectAllCheckBox = new JCheckBox("Select All");
		
		// Add action listener to "Select All" checkbox
		selectAllCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean selectAll = selectAllCheckBox.isSelected();
				for (JCheckBox checkBox : optionCheckBoxes) {
					checkBox.setSelected(selectAll);
					selectedOptions2.add(checkBox.getText());
				}
			}
		});
		
		optionsPanel.add(selectAllCheckBox);
		
		// Add checkboxes for each option
		for (String option : options) {
			JCheckBox checkBox = new JCheckBox(option);
			optionCheckBoxes.add(checkBox);
			optionsPanel.add(checkBox);
		}

		// Set up the "Submit" button
		submitButton = new JButton("Submit");
		
		// Add action listener to "Submit" button
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedOptions = "Selected options:\n";
				
				boolean selectAll = selectAllCheckBox.isSelected();
				if (selectAll) {
					selectedOptions += "Select All\n";
				}
				for (JCheckBox checkBox : optionCheckBoxes) {
					if (checkBox.isSelected() && !selectAll) {
						selectedOptions += checkBox.getText() + "\n";
						selectedOptions2.add(checkBox.getText());
					}
				}
				
				// Display selected options in message dialog and filter schedule list
				JOptionPane.showMessageDialog(SelectUCsTest.this, selectedOptions);
				dispose();
				
				new SaveOrDisplay(filterScheduleByCourse(list,selectedOptions2));
			}
		});

		// Add components to main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(optionsPanel, BorderLayout.CENTER);
		mainPanel.add(submitButton, BorderLayout.SOUTH);

		// Set up main window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**

	Filters a ScheduleList by a list of selected courses.

	@param scheduleList the ScheduleList to be filtered

	@param courseList the list of courses to be filtered by

	@return a new ScheduleList containing only the schedules that match the selected courses
	*/
	public ScheduleList filterScheduleByCourse(ScheduleList scheduleList, List<String> courseList) {
	List<Schedule> filteredScheduleList = new ArrayList<Schedule>();

	for (Schedule schedule : scheduleList.getSchedules()) {
	if (courseList.contains(schedule.getUnidadeCurricular())) {
	filteredScheduleList.add(schedule);
	}
	}
	return new ScheduleList(filteredScheduleList);
	}

}