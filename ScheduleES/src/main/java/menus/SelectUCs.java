package menus;

import javax.swing.*;

import WebSchedule.MonthSchedule;
import converters.CsvToJsonConverter;
import converters.JsonToCsvConverter;
import modules.Horario;
import modules.Schedule;
import modules.ScheduleList;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class SelectUCs extends JFrame { // adicionar uma opcao para selecionar todas
	private List<JCheckBox> optionCheckBoxes;
	private JButton submitButton;
	private ScheduleList lista;
	private ScheduleList list;

	public SelectUCs(final File file, List<String> options, final String path, final int typeFile) throws IOException {
		
		super("Menu Swing");
		// Cria o painel de opções
		JPanel optionsPanel = new JPanel(new GridLayout(options.size() + 1, 1));
		optionCheckBoxes = new ArrayList<>();
		final JCheckBox selectAllCheckBox = new JCheckBox("Selecionar Todos");
		selectAllCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean selectAll = selectAllCheckBox.isSelected();
				for (JCheckBox checkBox : optionCheckBoxes) {
					checkBox.setSelected(selectAll);
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
				List<String> selectedOptions2 = new ArrayList<String>();
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
				
				if (typeFile == 1) {
					try {
						list = JsonToCsvConverter.jsonToCsvConverted(file, path, selectedOptions2);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else {
					try {
						list = CsvToJsonConverter.CsvToJsonConverted(file, path, selectedOptions2);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
				JOptionPane.showMessageDialog(SelectUCs.this, selectedOptions);
				dispose();
				MonthSchedule.initializeSchdule(list.getSchedules());
				
				
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
	
	
}
