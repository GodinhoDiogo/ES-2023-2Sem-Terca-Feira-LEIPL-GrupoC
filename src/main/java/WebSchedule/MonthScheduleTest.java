package WebSchedule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import modules.Schedule;

public class MonthScheduleTest extends JPanel {
// instance variables
	private JPanel headerPanel;
	private JLabel titleLabel;
	private JPanel contentPanel;
	private JPanel footerPanel;
	private JButton previousButton;
	private JButton nextButton;
	private List<Schedule> scheduleList;
	private int currentWeekIndex;
	private List<Date> currentWeekDays;
	private int month;
	private List<List<String>> aulas = new ArrayList<List<String>>();
	private JButton dayButton;
	private int currentAulaIndex = 0;
	private JButton monthButton;

	/**
	 * Constructs a MonthSchedule object with a specified list of Schedule objects
	 * and list of lists of Date objects representing the week days for each month
	 * of the year.
	 *
	 * @param scheduleList         a List of Schedule objects containing information
	 *                             about the classes.
	 * @param novemberWeekDaysList a List of lists of Date objects containing the
	 *                             week days for November.
	 */
	public MonthScheduleTest(final List<Schedule> scheduleList, final List<List<Date>> novemberWeekDaysList) {
		// initialize instance variables
		this.scheduleList = scheduleList;
		this.currentWeekDays = novemberWeekDaysList.get(0);
		this.currentWeekIndex = 0;
		this.month = 11;

		// set the layout of the panel
		setLayout(new BorderLayout());

		// create the header panel
		headerPanel = new JPanel(new BorderLayout());
		titleLabel = new JLabel("Horário das Aulas");
		headerPanel.add(titleLabel, BorderLayout.CENTER);
		add(headerPanel, BorderLayout.NORTH);
		monthButton = new JButton("View Month");
		headerPanel.add(monthButton, BorderLayout.EAST);

		// create the content panel
		contentPanel = new JPanel(new GridLayout(0, 8));
		add(contentPanel, BorderLayout.CENTER);

		// create the footer panel
		footerPanel = new JPanel(new BorderLayout());
		previousButton = new JButton("<<");
		footerPanel.add(previousButton, BorderLayout.WEST);
		nextButton = new JButton(">>");
		footerPanel.add(nextButton, BorderLayout.EAST);
		add(footerPanel, BorderLayout.SOUTH);

		dayButton = new JButton("Visualize by Day");
		footerPanel.add(dayButton, BorderLayout.CENTER);

		// populate the content panel with the schedule data
		populateSchedule(scheduleList, currentWeekDays);

		/**
		 * Adds an action listener to the previous button that decrements the current
		 * week index, updates the current week days and the month displayed on the
		 * calendar. If the current week index goes below 0, the previous month's week
		 * days are retrieved and the calendar is updated to display the previous month.
		 * <p>
		 * Adds an action listener to the next button that calls the
		 * {@link #nextButtonWeek()} method to increment the current week index and
		 * update the calendar.
		 * <p>
		 * Adds an action listener to the day button that calls the
		 * {@link #aulasFrame()} method if the current aula index is not null.
		 * <p>
		 * Adds an action listener to the month button that calls the
		 * {@link #monthFrame()} method.
		 */
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentWeekIndex--;
				if (currentWeekIndex < 0) {
					// get the previous month's week days
					currentWeekDays = getMonthWeekDays(2022, month - 1)
							.get(getMonthWeekDays(2022, month - 1).size() - 1);
					populateSchedule(scheduleList, currentWeekDays);
					currentWeekIndex = getMonthWeekDays(2022, month - 1).size() - 1;
					month--;
					System.out.println("Previous month!");
				} else {
					currentWeekDays = getMonthWeekDays(2022, month).get(currentWeekIndex);
					populateSchedule(scheduleList, currentWeekDays);
					System.out.println("Previous week!");
				}
			}
		});

		/**
		 * Adds an action listener to the next button that calls the
		 * {@link #nextButtonWeek()} method to increment the current week index and
		 * update the calendar.
		 */
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextButtonWeek();
			}
		});

		/**
		 * Adds an action listener to the day button that calls the
		 * {@link #aulasFrame()} method if the current aula index is not null.
		 */
		dayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (aulas.get(currentAulaIndex) == null) {
					// do nothing
				} else {
					aulasFrame();
				}
			}
		});

		/**
		 * Adds an action listener to the month button that calls the
		 * {@link #monthFrame()} method.
		 */
		monthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				monthFrame();
			}
		});

	}

	/**
	 * Increments the current week index and updates the schedule display
	 * accordingly. If the current week index is less than the number of weeks in
	 * the current month, the next week's schedule is displayed. If the current week
	 * index is the last week of the current month, the first week of the next month
	 * is displayed.
	 */
	private void nextButtonWeek() {
		currentWeekIndex++;
		if (currentWeekIndex < getMonthWeekDays(2022, month).size() - 1) {
			System.out.println();
			currentWeekDays = getMonthWeekDays(2022, month).get(currentWeekIndex);
			populateSchedule(scheduleList, currentWeekDays);
			System.out.println("Next week!");

		} else {
			currentWeekDays = getMonthWeekDays(2022, month + 1).get(0);
			month++;
			populateSchedule(scheduleList, currentWeekDays);
			currentWeekIndex = 0;
			System.out.println("Next month!");
		}
	}

	/**
	 * 
	 * Populates the schedule in the UI based on a list of schedules and a list of
	 * current week days.
	 * 
	 * @param schedules       The list of schedules to populate the UI with.
	 * 
	 * @param currentWeekDays The list of current week days.
	 */
	private void populateSchedule(List<Schedule> schedules, List<Date> currentWeekDays) {
		aulas.clear();
		contentPanel.removeAll();
		String[] result = convert(currentWeekDays); // the result are the days of the week
// Adds the days of the week to the first row of the panel
		contentPanel.add(new JLabel(""));
		contentPanel.add(new JLabel(result[0]));
		contentPanel.add(new JLabel(result[1]));
		contentPanel.add(new JLabel(result[2]));
		contentPanel.add(new JLabel(result[3]));
		contentPanel.add(new JLabel(result[4]));
		contentPanel.add(new JLabel(result[5]));
		contentPanel.add(new JLabel(result[6]));

		String[] horarios = { "08:00:00", "09:30:00", "11:00:00", "13:00:00", "14:30:00", "16:00:00", "17:30:00",
				"18:00:00" };
		String[] dates = convert2(result);

// Iterates through each time slot and each day of the week
		for (String horario : horarios) {
			JLabel label = new JLabel(horario);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
			contentPanel.add(label);
			for (int j = 0; j < 7; j++) {
				JLabel cellLabel = new JLabel();
				cellLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0))); // adds border
// Iterates through each schedule to find matching time slots and days of the week
				for (Schedule schedule : schedules) {
					System.out.println(schedule.getHorarioInicioAula() + " " + schedule.getDiaSemana() + " "
							+ schedule.getDataAula());
					if (schedule.getHorarioInicioAula().equals(horario)
							&& schedule.getDiaSemana().equals(getDiaSemana(j))
							&& schedule.getDataAula().equals(dates[j])) {
						cellLabel.setText(schedule.getUnidadeCurricular());
						List<String> lista = new ArrayList<String>();
						lista.add(schedule.getDiaSemana());
						lista.add(schedule.getHorarioInicioAula());
						lista.add(schedule.getDataAula());
						lista.add(schedule.getUnidadeCurricular());
						aulas.add(lista);
						System.out.println("Nao me estas a entrar é aqui pois nao?");
					}
				}
				cellLabel.setHorizontalAlignment(JLabel.CENTER);
				cellLabel.setVerticalAlignment(JLabel.CENTER);
				contentPanel.add(cellLabel);
			}
		}
		contentPanel.revalidate(); // updates the UI
		contentPanel.repaint();
	}

	/**
	 * 
	 * Converts an array of week days in the format "Weekday, DD/MM/YYYY" to an
	 * array of dates in the format "DD/MM/YYYY".
	 * 
	 * @param weekDays an array of week days in the format "Weekday, DD/MM/YYYY"
	 * 
	 * @return an array of dates in the format "DD/MM/YYYY"
	 */
	public String[] convert2(String[] weekDays) {
		String[] dates = new String[weekDays.length];

		for (int i = 0; i < weekDays.length; i++) {
			String[] parts = weekDays[i].split(", ");
			if (parts.length == 2) {
				dates[i] = parts[1];
			}
		}

		return dates;
	}

	/**
	 * 
	 * Returns the abbreviation of a week day based on its index.
	 * 
	 * @param j the index of the week day
	 * @return the abbreviation of the week day
	 */
	private String getDiaSemana(int j) {
		switch (j) {
		case 0:
			return "Seg";
		case 1:
			return "Ter";
		case 2:
			return "Qua";
		case 3:
			return "Qui";
		case 4:
			return "Sex";
		case 5:
			return "Sábado";
		case 6:
			return "Domingo";
		default:
			return "";
		}
	}

	/**
	 * 
	 * This method returns a list of lists containing the dates for each week of a
	 * given month and year.
	 * 
	 * The dates are represented as instances of the Date class.
	 * 
	 * @param year  The year for which the month should be retrieved
	 * 
	 * @param month The month for which the dates should be retrieved (January is 1,
	 *              February is 2, and so on)
	 * 
	 * @return A list of lists containing the dates for each week of the month
	 */
	public static List<List<Date>> getMonthWeekDays(int year, int month) {
		// Cria uma lista para armazenar as semanas do mês
		List<List<Date>> monthWeekDaysList = new ArrayList<List<Date>>();

		// Cria um calendário para representar o primeiro dia do mês
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1); // subtrai 1 do mês para ajustar ao formato de calendário (janeiro = 0)

		// Verifica se o primeiro dia do mês é uma segunda-feira
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek != Calendar.MONDAY) {
			// Se não for, adiciona os dias restantes da primeira semana separadamente
			int daysUntilMonday = 9 - dayOfWeek; // 9 porque estamos considerando que segunda é o segundo dia da semana
			// (e não o primeiro)
			List<Date> firstWeekDays = new ArrayList<Date>();
			for (int i = 0; i < daysUntilMonday; i++) {
				firstWeekDays.add(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			monthWeekDaysList.add(firstWeekDays);
		}

		// Itera pelas semanas do mês, adicionando os dias da semana a cada semana
		while (cal.get(Calendar.MONTH) == month - 1) {
			List<Date> weekDays = new ArrayList<Date>();
			for (int i = 0; i < 7; i++) {
				weekDays.add(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			monthWeekDaysList.add(weekDays);
		}

		// Verifica se o último dia do mês é um domingo
		int lastDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (lastDayOfWeek != Calendar.SUNDAY) {
			// Se não for, adiciona os dias restantes da última semana separadamente
			int daysFromMonday = 7 - (lastDayOfWeek - 2); // 2 porque estamos considerando que segunda é o segundo dia
			// da semana (e não o primeiro)
			List<Date> lastWeekDays = new ArrayList<Date>();
			cal.add(Calendar.DAY_OF_MONTH, -daysFromMonday);
			for (int i = 0; i < daysFromMonday; i++) {
				lastWeekDays.add(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			monthWeekDaysList.add(lastWeekDays);
		}

		return monthWeekDaysList;
	}

	/**
	 * 
	 * Returns a list of all dates in the given month and year.
	 * 
	 * @param year  the year of the month
	 * 
	 * @param month the month to retrieve dates for, where 1 represents January and
	 *              12 represents December
	 * 
	 * @return a list of all dates in the given month and year
	 */
	public static List<Date> getMonthDates(int year, int month) {
		List<Date> monthDatesList = new ArrayList<Date>();

		// Create a calendar instance for the first day of the month
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1); // Subtract 1 from the month to adjust to calendar format (January = 0)

		// Add all dates in the month to the list
		while (cal.get(Calendar.MONTH) == month - 1) {
			monthDatesList.add(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}

		return monthDatesList;
	}

	/**
	 * 
	 * Converte uma lista de objetos Date em um array de strings formatados para
	 * exibição. O array de saída possui 7 elementos, correspondendo aos dias da
	 * semana de segunda-feira a domingo. Se uma data para um dia da semana
	 * específico não estiver presente na lista de entrada, o array conterá apenas o
	 * nome do dia da semana na posição correspondente.
	 * 
	 * @param currentWeekDays a lista de objetos Date que representa os dias da
	 *                        semana atual
	 * @return um array de strings formatados contendo as datas da semana
	 */
	public static String[] convert(List<Date> currentWeekDays) {
		String[] result = new String[7];
		// Define o formato de data para "segunda-feira, 05/05/2023"
		SimpleDateFormat format = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("pt", "BR"));

		// Cria um calendário com a primeira data da lista
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentWeekDays.get(0));

		// Encontra o índice do dia da semana atual na lista
		int startDayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 2; // 0 para segunda-feira, 1 para terça-feira, etc.

		// Preenche o array com as datas da semana, começando pela segunda-feira
		for (int i = 0; i < 7; i++) {
			if (i < startDayIndex || i >= startDayIndex + currentWeekDays.size()) {
				// Não há data para esse dia da semana na entrada, coloca o nome do dia da
				// semana na posição correspondente
				Calendar dayOfWeek = Calendar.getInstance();
				dayOfWeek.set(Calendar.DAY_OF_WEEK, i + 2); // Adiciona 2 ao índice para converter para o valor do
															// calendário (2 para segunda-feira, 3 para terça-feira,
															// etc.)
				result[i] = format.format(dayOfWeek.getTime()).split(", ")[0]; // Pega apenas o nome do dia da semana
			} else {
				// Converte a data atual para a string formatada e coloca no array
				result[i] = format.format(currentWeekDays.get(i - startDayIndex));
			}
		}

		// Se houver uma data para domingo na entrada, coloca no último índice do array
		int lastDayIndex = startDayIndex + currentWeekDays.size() - 1;
		if (lastDayIndex == 6) {
			result[6] = format.format(currentWeekDays.get(currentWeekDays.size() - 1));
		}

		return result;
	}

	/**
	 * 
	 * Displays the schedule of classes for the current day. Clears the headerPanel,
	 * contentPanel and footerPanel, and adds a "Next Day" button to the
	 * footerPanel. Displays the first class of the sorted list of classes, and adds
	 * an ActionListener to the "Next Day" button that displays the next class in
	 * the list when clicked. When there are no more classes to display, resets the
	 * headerPanel, contentPanel, and footerPanel to their original layout and adds
	 * the navigation buttons and the dayButton.
	 */
	public void aulasFrame() {

		headerPanel.removeAll();
		contentPanel.removeAll();
		footerPanel.removeAll();
		revalidate();
		repaint();
		// contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton nextButton2 = new JButton("Next Day");
		footerPanel.add(nextButton2, BorderLayout.CENTER);

		final JLabel aulaLabel = new JLabel();
		contentPanel.add(aulaLabel);
		aulas = ordenar(aulas);
		currentAulaIndex = 0;
		exibirAula(aulas.get(currentAulaIndex));

		nextButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentAulaIndex++;
				contentPanel.removeAll();
				if (currentAulaIndex < aulas.size()) {
					System.out.println(currentAulaIndex);
					exibirAula(aulas.get(currentAulaIndex));
				} else {
					// footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
					currentAulaIndex = 0;
					headerPanel.removeAll();
					contentPanel.removeAll();
					footerPanel.removeAll();
					setSize(800, 600);
					headerPanel.add(titleLabel, BorderLayout.CENTER);
					add(headerPanel, BorderLayout.NORTH);
					add(contentPanel, BorderLayout.CENTER);
					footerPanel.add(previousButton, BorderLayout.WEST);
					footerPanel.add(nextButton, BorderLayout.EAST);
					add(footerPanel, BorderLayout.SOUTH);
					footerPanel.add(dayButton, BorderLayout.CENTER);
					headerPanel.add(monthButton, BorderLayout.EAST);
					populateSchedule(scheduleList, currentWeekDays);

				}
			}
		});
	}

	/**
	 * 
	 * Ordena as listas de aulas por dias da semana, seguindo a ordem de segunda a
	 * sexta-feira.
	 * 
	 * @param aulas lista de listas de strings contendo informações sobre as aulas,
	 *              onde o primeiro elemento de cada lista é o dia da semana da aula
	 * 
	 * @return lista de listas de strings contendo todas as aulas ordenadas por dias
	 *         da semana, onde as aulas de segunda-feira vêm primeiro, seguidas
	 *         pelas aulas de terça-feira, quarta-feira, quinta-feira e sexta-feira
	 */
	public static List<List<String>> ordenar(List<List<String>> aulas) {
		List<List<String>> aulasSeg = new ArrayList<List<String>>(); // lista vazia para armazenar as aulas de
																		// segunda-feira
		List<List<String>> aulasTer = new ArrayList<List<String>>(); // lista vazia para armazenar as aulas de
																		// terça-feira
		List<List<String>> aulasQua = new ArrayList<List<String>>(); // lista vazia para armazenar as aulas de
																		// quarta-feira
		List<List<String>> aulasQui = new ArrayList<List<String>>(); // lista vazia para armazenar as aulas de
																		// quinta-feira
		List<List<String>> aulasSex = new ArrayList<List<String>>(); // lista vazia para armazenar as aulas de
																		// sexta-feira

		// Percorre a lista de aulas e adiciona as aulas nas suas respectivas listas de
		// acordo com o dia da semana
		for (List<String> aula : aulas) {
			if (aula.get(0).equals("Seg")) {
				aulasSeg.add(aula);
			} else if (aula.get(0).equals("Ter")) {
				aulasTer.add(aula);
			} else if (aula.get(0).equals("Qua")) {
				aulasQua.add(aula);
			} else if (aula.get(0).equals("Qui")) {
				aulasQui.add(aula);
			} else if (aula.get(0).equals("Sex")) {
				aulasSex.add(aula);
			}
		}

		// Cria uma nova lista que vai conter todas as aulas de seguida por todas as
		// aulas de ter, quarta-feira, quinta-feira e sexta-feira, nesta ordem
		List<List<String>> aulasSegTer = new ArrayList<List<String>>();
		aulasSegTer.addAll(aulasSeg);
		aulasSegTer.addAll(aulasTer);
		aulasSegTer.addAll(aulasQua);
		aulasSegTer.addAll(aulasQui);
		aulasSegTer.addAll(aulasSex);

		// Retorna a lista de aulas ordenada
		return aulasSegTer;
	}

	/**
	 * 
	 * Inicializa a exibição do horário das aulas em um JFrame utilizando um painel
	 * de calendário.
	 * 
	 * @param list a lista de objetos Schedule contendo informações sobre as aulas
	 */
	public static void initializeSchdule(List<Schedule> list) {
		// Obtém a lista de datas correspondentes aos dias da semana em um determinado
		// mês e ano
		List<List<Date>> d = getMonthWeekDays(2022, 11);

		// Cria um painel de calendário com a lista de objetos Schedule e a lista de
		// datas obtida anteriormente
		MonthSchedule schedulePanel = new MonthSchedule(list, d);

		// Cria um novo JFrame com o título "Horário das Aulas" e adiciona o painel de
		// calendário ao seu content pane
		JFrame frame = new JFrame("Horário das Aulas");
		frame.getContentPane().add(schedulePanel);

		// Configura o tamanho do JFrame, define a operação padrão de fechamento ao
		// clicar no botão de fechar e torna o JFrame visível
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * 
	 * Populates the schedule panel with schedules for the current week days.
	 * 
	 * @param schedules       a List of Schedule objects to be displayed on the
	 *                        schedule panel
	 * 
	 * @param currentWeekDays a List of Date objects representing the current week
	 *                        days
	 */
	private void populateSchedule2(List<Schedule> schedules, List<Date> currentWeekDays) {
		aulas.clear(); // clears aulas ArrayList
		// contentPanel.removeAll();

		// Converts the currentWeekDays to an array of Strings
		String[] result = convert(currentWeekDays);

		// Adds the days of the week to the first row of the contentPanel
		contentPanel.add(new JLabel(""));
		contentPanel.add(new JLabel(result[0]));
		contentPanel.add(new JLabel(result[1]));
		contentPanel.add(new JLabel(result[2]));
		contentPanel.add(new JLabel(result[3]));
		contentPanel.add(new JLabel(result[4]));
		contentPanel.add(new JLabel(result[5]));
		contentPanel.add(new JLabel(result[6]));

		// An array of Strings representing the time slots for the schedule
		String[] horarios = { "08:00:00", "09:30:00", "11:00:00", "13:00:00", "14:30:00", "16:00:00", "17:30:00",
				"18:00:00" };

		// Converts the currentWeekDays to an array of Strings
		final String[] dates = convert2(result);

		// Loops through each time slot and day of the week and adds the corresponding
		// schedules to the contentPanel
		for (final String horario : horarios) {
			// Adds the time slot to the contentPanel
			JLabel label = new JLabel(horario);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
			contentPanel.add(label);
			for (int j = 0; j < 7; j++) {
				// Adds the schedule information to a cell of the contentPanel
				final int i = j;
				JLabel cellLabel = new JLabel();
				for (Schedule schedule : schedules) {
					cellLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0))); // Adds a border to the
																								// cell
					if (schedule.getHorarioInicioAula().equals(horario)
							&& schedule.getDiaSemana().equals(getDiaSemana(j))
							&& schedule.getDataAula().equals(dates[j])) {

						cellLabel.setText(schedule.getUnidadeCurricular()); // Sets the label text to the name of the
																			// course
						List<String> lista = new ArrayList<String>();
						lista.add(schedule.getDiaSemana());
						lista.add(schedule.getHorarioInicioAula());
						lista.add(schedule.getDataAula());
						lista.add(schedule.getUnidadeCurricular());
						aulas.add(lista); // Adds the schedule information to the aulas ArrayList
					}
				}

				// Checks if there are multiple schedules in the same time slot and displays a
				// button to show them
				int count = 0;
				for (List<String> aula : aulas) {
					if (aula.get(1).equals(horario) && aula.get(0).equals(getDiaSemana(j))
							&& aula.get(2).equals(dates[j])) {
						count++;
					}
				}

				/**
				 * 
				 * If there are at least two classes at the same time, create a button to
				 * display the clashed classes when clicked.
				 * 
				 * @param count     the number of classes at the same time
				 * @param horario   the start time of the classes
				 * @param i         the index of the current day of the week
				 * @param dates     an array of dates for the current week
				 * @param aulas     a list of scheduled classes
				 * @param cellLabel the JLabel for the current cell in the schedule table
				 */
				if (count >= 2) {
					JButton seeClashesButton = new JButton("Aula sobreposta");
					seeClashesButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
// Create a pop-up window to display the clashed classes
							JFrame clashPopup = new JFrame("Clashed Classes");
							clashPopup.setSize(300, 200);
							clashPopup.setLocationRelativeTo(null);
							JTextArea clashTextArea = new JTextArea();
							for (List<String> aula : aulas) {
// Check for classes that clash with the current class
								if (aula.get(1).equals(horario) && aula.get(0).equals(getDiaSemana(i))
										&& aula.get(2).equals(dates[i])) {
									clashTextArea.append(aula.get(3) + "\n");
								}
							}
							clashPopup.add(new JScrollPane(clashTextArea));
							clashPopup.setVisible(true);
						}
					});
// Set the layout of the cell to add the clashed classes button
					cellLabel.setLayout(new BorderLayout());
					cellLabel.add(seeClashesButton, BorderLayout.NORTH);
				}
				cellLabel.setHorizontalAlignment(JLabel.CENTER);
				cellLabel.setVerticalAlignment(JLabel.CENTER);
				contentPanel.add(cellLabel);
			}
		}
		contentPanel.revalidate(); // Atualiza a interface do usuário
		contentPanel.repaint();
	}

	/**
	 * 
	 * Displays the details of a class in the content panel.
	 * 
	 * @param aula The class to be displayed.
	 */
	private void exibirAula(List<String> aula) {
		System.out.println("Entras aqui!");
		// contentPanel.removeAll();
		contentPanel.add(new JLabel("Dia da semana: "));
		contentPanel.add(new JLabel(aula.get(0)));
		contentPanel.add(new JLabel("Hora de Inicio: "));
		contentPanel.add(new JLabel(aula.get(1)));
		contentPanel.add(new JLabel("Data:"));
		contentPanel.add(new JLabel(aula.get(2)));
		contentPanel.add(new JLabel("Unidade Curricular:"));
		contentPanel.add(new JLabel(aula.get(3)));
		revalidate();
		repaint();
	}

	/**
	 * 
	 * Removes existing components from header, content, and footer panels.
	 * 
	 * Populates schedule for each week of the current month. Creates buttons for
	 * 
	 * previous, week table, and next month, and adds event listeners to them.
	 * 
	 * @param None
	 * 
	 * @return None
	 */
	private void monthFrame() {
		// Remove existing components
		headerPanel.removeAll();
		contentPanel.removeAll();
		footerPanel.removeAll();
		revalidate();
		repaint();
		currentWeekIndex = 0;

		// Populate schedule for each week of the month
		for (int i = 0; i < getMonthWeekDays(2022, month).size() - 1; i++) {
			currentWeekDays = getMonthWeekDays(2022, month).get(currentWeekIndex);
			populateSchedule2(scheduleList, currentWeekDays);
			currentWeekIndex++;
		}

		currentWeekIndex = 0;

		// Create previous, week table, and next buttons
		JButton prevButton = new JButton("Previous Month");
		JButton weekTableButton = new JButton("Week Table");
		JButton nextButton2 = new JButton("Next Month");

		// Add event listeners to buttons
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPanel.removeAll();
				revalidate();
				repaint();
				month--;
				currentWeekIndex = 0;
				for (int i = 0; i < getMonthWeekDays(2022, month).size() - 1; i++) {
					currentWeekDays = getMonthWeekDays(2022, month).get(currentWeekIndex);
					populateSchedule2(scheduleList, currentWeekDays);
					currentWeekIndex++;
				}
			}
		});

		weekTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentAulaIndex = 0;
				headerPanel.removeAll();
				contentPanel.removeAll();
				footerPanel.removeAll();
				setSize(800, 600);
				headerPanel.add(titleLabel, BorderLayout.CENTER);
				add(headerPanel, BorderLayout.NORTH);
				add(contentPanel, BorderLayout.CENTER);
				footerPanel.add(previousButton, BorderLayout.WEST);
				footerPanel.add(nextButton, BorderLayout.EAST);
				add(footerPanel, BorderLayout.SOUTH);
				footerPanel.add(dayButton, BorderLayout.CENTER);
				headerPanel.add(monthButton, BorderLayout.EAST);
				populateSchedule(scheduleList, currentWeekDays);
			}
		});

		nextButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPanel.removeAll();
				revalidate();
				repaint();
				month++;
				currentWeekIndex = 0;
				for (int i = 0; i < getMonthWeekDays(2022, month).size() - 1; i++) {
					currentWeekDays = getMonthWeekDays(2022, month).get(currentWeekIndex);
					populateSchedule2(scheduleList, currentWeekDays);
					currentWeekIndex++;
				}
			}
		});

		// Add buttons to footer panel
		footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		footerPanel.add(prevButton);
		footerPanel.add(weekTableButton);
		footerPanel.add(nextButton2);

		// Repaint view
		revalidate();
		repaint();
	}

}
