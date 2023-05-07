package menus;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import converters.CsvToJsonConverter;
import converters.JsonToCsvConverter;
import modules.ScheduleList;
import java.io.FileInputStream;
import java.io.InputStream;

public class ChooseImportTest extends JFrame {
	private JTextField textField;
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;
	private JRadioButton radioButton3;

	/**
	 * Constructs a new ChooseImport object and initializes its components. Creates
	 * a window that allows the user to select between importing a JSON file, a CSV
	 * file or a web call. Depending on the selected option, a file chooser or a
	 * text field will be displayed to allow the user to enter their file path or
	 * URL. The Submit button triggers the conversion of the selected file or URL
	 * content into a ScheduleList object. Duplicate words from the ScheduleList
	 * object are removed, and the SelectUCs window is displayed. If an exception
	 * occurs during the conversion process, the error will be printed in the
	 * console.
	 */
	public ChooseImportTest() {
		super("Import File");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		// Create text field
		textField = new JTextField(20);
		radioButton1 = new JRadioButton("Import Json");
		radioButton2 = new JRadioButton("Import Csv");
		radioButton3 = new JRadioButton("Import From webCall");

		// Create button group so only one radio button can be selected
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButton1);
		buttonGroup.add(radioButton2);
		buttonGroup.add(radioButton3);

		// Create submit button
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ScheduleList ss = null;
					JFileChooser fileChooser = new JFileChooser();
					if (!radioButton3.isSelected()) {
						int result = fileChooser.showOpenDialog(ChooseImportTest.this);
						if (result == JFileChooser.APPROVE_OPTION) {
							File selectedFile = fileChooser.getSelectedFile();
							if (radioButton1.isSelected()) {
								try (InputStream jsonInputStream = new FileInputStream(selectedFile)) {
									ss = JsonToCsvConverter.jsonToCsvConverted(jsonInputStream, null);
								}
							} else if (radioButton2.isSelected()) {
								try (InputStream csvInputStream = new FileInputStream(selectedFile)) {
									ss = CsvToJsonConverter.CsvToJsonConverted(csvInputStream, null);
								}
							}
						}
					}

					if (radioButton3.isSelected()) {
						String text = textField.getText();
						ss = lerHorario(text);
					}

					System.out.println(ss);
					List<String> cadeiras = removeDuplicateWords(ss);
					dispose();
					new SelectUCs(ss, cadeiras);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Add components to window
		add(radioButton1);
		add(radioButton2);
		add(radioButton3);
		add(textField);
		add(submitButton);

		// Set window properties
		setSize(300, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * 
	 * Reads a schedule from a webcal URL and returns a ScheduleList object
	 * containing the schedule. This function replaces "webcal" with "https" in the
	 * URL to avoid the "unsupported URL" exception.
	 * 
	 * @param uri the webcal URL from which to read the schedule
	 * @return a ScheduleList object containing the schedule read from the URL
	 * @throws IOException if an I/O error occurs while reading from the URL
	 */

	public ScheduleList lerHorario(String uri) throws IOException {
		System.out.println("Vou ler o url");
		URL url = new URL(uri.replace("webcal", "https"));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder content = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine).append("\n");
		}
		in.close();
		con.disconnect();

		return ScheduleList.fromWebcalString(content.toString());
	}

	/**
	 * 
	 * Removes duplicate words from the list of schedules in a ScheduleList object.
	 * This function creates a new list with only the unique words from the original
	 * list, and returns the first 5 unique words (or all of them if there are fewer
	 * than 5) in a new list.
	 * 
	 * @param list a ScheduleList object containing the list of schedules
	 * @return a List<String> object containing the unique words from the list of
	 *         schedules
	 */

	public List<String> removeDuplicateWords(ScheduleList list) {
		List<String> schedules = new ArrayList<String>();

		for (int i = 0; i < list.getSchedules().size(); i++) {
			schedules.add(list.getSchedules().get(i).getUnidadeCurricular());
		}
		List<String> listaUnica = new ArrayList<String>(new HashSet<String>(schedules));

		List<String> schedules2 = new ArrayList<String>();
		for (int i = 0; i < 5 && i < listaUnica.size(); i++) {
			schedules2.add(listaUnica.get(i));
		}
		return schedules2; // dar return ao listaUnica
	}

	public static void main(String[] args) {
		new ChooseImport();
	}

}
