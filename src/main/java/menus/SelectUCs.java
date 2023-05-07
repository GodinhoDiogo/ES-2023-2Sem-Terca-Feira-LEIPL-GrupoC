package menus;

import javax.swing.*;

import modules.Schedule;
import modules.ScheduleList;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SelectUCs extends JFrame { // adicionar uma opcao para selecionar todas
	private List<JCheckBox> optionCheckBoxes;
	private JButton submitButton;
	private ScheduleList list;
	List<String> selectedOptions2 = new ArrayList<String>();

	public SelectUCs(ScheduleList sl, List<String> options) throws IOException {
		
		super("Menu Swing");
		// Cria o painel de opções
		list = sl;
		JPanel optionsPanel = new JPanel(new GridLayout(options.size() + 1, 1));
		optionCheckBoxes = new ArrayList<>();
		final JCheckBox selectAllCheckBox = new JCheckBox("Selecionar Todos");
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
		for (String option : options) {
			JCheckBox checkBox = new JCheckBox(option);
			optionCheckBoxes.add(checkBox);
			optionsPanel.add(checkBox);
		}

		// Cria o botão de submissão
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Ação a ser executada ao clicar no botão de submissão
				String selectedOptions = "Opções selecionadas:\n";
				
				boolean selectAll = selectAllCheckBox.isSelected();
				if (selectAll) {
					selectedOptions += "Selecionar Todos\n";
				}
				for (JCheckBox checkBox : optionCheckBoxes) {
					if (checkBox.isSelected() && !selectAll) {
						selectedOptions += checkBox.getText() + "\n";
						selectedOptions2.add(checkBox.getText());
					}
				}
				
				
				JOptionPane.showMessageDialog(SelectUCs.this, selectedOptions);
				dispose();
				
				new SaveOrDisplay(filterScheduleByCourse(list,selectedOptions2));
				
				
			}
		});

		// Adiciona os componentes ao painel principal
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(optionsPanel, BorderLayout.CENTER);
		mainPanel.add(submitButton, BorderLayout.SOUTH);

		// Configura a janela principal
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		// lista = CsvToJsonConverter.CsvToJsonConverted(file, path);
	}
	
	public ScheduleList filterScheduleByCourse(ScheduleList scheduleList, List<String> courseList) {
	    List<Schedule> filteredScheduleList = new ArrayList<>();

	    for (Schedule schedule : scheduleList.getSchedules()) {
	        if (courseList.contains(schedule.getUnidadeCurricular())) {
	            filteredScheduleList.add(schedule);
	        }
	    }
	    return new ScheduleList(filteredScheduleList);
	}


	
	
	
}
