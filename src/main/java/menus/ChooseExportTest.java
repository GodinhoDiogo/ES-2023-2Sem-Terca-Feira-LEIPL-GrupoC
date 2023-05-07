
package menus;
/**

A classe ChooseExport é responsável por exibir uma janela com opções para exportar
um objeto ScheduleList em um arquivo CSV ou JSON.
@author [seu nome aqui]
@version 1.0
*/
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import modules.ScheduleList;
import utils.SaveFile;

public class ChooseExportTest extends JFrame {
	// Radio buttons para selecionar o tipo de arquivo a ser exportado
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;

	// Referência para o objeto ScheduleList a ser exportado
	private ScheduleList ss;

	/**
	 * Construtor da classe ChooseExport. Cria a janela com as opções de exportação.
	 * 
	 * @param ss objeto ScheduleList a ser exportado
	 */
	public ChooseExportTest(final ScheduleList ss) {
		super("Export File");
		this.ss = ss;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		// Criar os radio buttons
		radioButton1 = new JRadioButton("Export Json");
		radioButton2 = new JRadioButton("Export Csv");

		// Criar um grupo de botões para que apenas um possa ser selecionado
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButton1);
		buttonGroup.add(radioButton2);
		
		// Criar botão de exportação
		JButton exportButton = new JButton("Export");
		exportButton.addActionListener(new ActionListener() {
			/**
			 * Método actionPerformed que é chamado quando o botão de exportação é pressionado.
			 * Obtém o caminho do arquivo onde será salvo o objeto ScheduleList selecionado.
			 * Verifica qual tipo de arquivo foi selecionado e chama o método adequado para salvar o arquivo.
			 */
			public void actionPerformed(ActionEvent e) {
				String path = FileChooser.saveFile();
				if(radioButton1.isSelected()) {
					SaveFile.saveFileJson(ss, path);
				}else {
					SaveFile.saveFileCsv(ss, path);
				}
			}
		});

		// Adiciona os componentes à janela
		add(radioButton1);
		add(radioButton2);
		add(exportButton);

		// Define as propriedades da janela
		setSize(300, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}