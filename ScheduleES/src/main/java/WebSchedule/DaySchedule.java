package WebSchedule;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import modules.Horario;
import modules.Schedule;

public class DaySchedule extends JPanel {
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
	private List<List<String>> aulas = new ArrayList<>();
	private JButton dayButton;
	private int currentAulaIndex = 0;

	public DaySchedule(final List<Schedule> scheduleList, final List<List<Date>> novemberWeekDaysList) {
		this.scheduleList = scheduleList;
		this.currentWeekDays = novemberWeekDaysList.get(0);
		this.currentWeekIndex = 0;
		this.month = 11;

		setLayout(new BorderLayout());

		// Cria o painel do cabeçalho
		headerPanel = new JPanel(new BorderLayout());
		titleLabel = new JLabel("Horário das Aulas");
		headerPanel.add(titleLabel, BorderLayout.CENTER);
		add(headerPanel, BorderLayout.NORTH);

		// Cria o painel do conteúdo
		contentPanel = new JPanel(new GridLayout(0, 8));
		add(contentPanel, BorderLayout.CENTER);

		// Cria o painel do rodapé

		footerPanel = new JPanel(new BorderLayout());
		previousButton = new JButton("<<");
		footerPanel.add(previousButton, BorderLayout.WEST);
		nextButton = new JButton(">>");
		footerPanel.add(nextButton, BorderLayout.EAST);
		add(footerPanel, BorderLayout.SOUTH);

		dayButton = new JButton("Visualize by Day");
		footerPanel.add(dayButton, BorderLayout.CENTER);

		// Preenche o painel do conteúdo com os dados do horário de aulas
		populateSchedule(scheduleList, currentWeekDays);

		// Adiciona eventos de clique nos botões de navegação
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

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				nextButtonWeek();
			}
		});

		dayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				aulasFrame();
			}
		});

	}

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

	private void populateSchedule(List<Schedule> schedules, List<Date> currentWeekDays) {
		aulas.clear();
		contentPanel.removeAll();
		String[] result = convert(currentWeekDays); // o result sao os dias da semana
		// Adiciona os dias da semana na primeira linha do painel
		contentPanel.add(new JLabel(""));
		contentPanel.add(new JLabel(result[0]));
		contentPanel.add(new JLabel(result[1]));
		contentPanel.add(new JLabel(result[2]));
		contentPanel.add(new JLabel(result[3]));
		contentPanel.add(new JLabel(result[4]));
		contentPanel.add(new JLabel(result[5]));
		contentPanel.add(new JLabel(result[6]));

		String[] horarios = { "13:00:00", "14:30:00", "16:00:00", "17:30:00", "18:00:00" };
		String[] dates = convert2(result);

		for (String horario : horarios) {
			JLabel label = new JLabel(horario);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
			contentPanel.add(label);
			for (int j = 0; j < 7; j++) {
				JLabel cellLabel = new JLabel();
				for (Schedule schedule : schedules) {

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
					}
				}
				cellLabel.setHorizontalAlignment(JLabel.CENTER);
				cellLabel.setVerticalAlignment(JLabel.CENTER);
				contentPanel.add(cellLabel);
			}
		}
		contentPanel.revalidate(); // Atualiza a interface do usuário
		contentPanel.repaint();
	}

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

	public static List<List<Date>> getMonthWeekDays(int year, int month) {
		// Cria uma lista para armazenar as semanas do mês
		List<List<Date>> monthWeekDaysList = new ArrayList<>();

		// Cria um calendário para representar o primeiro dia do mês
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1); // subtrai 1 do mês para ajustar ao formato de calendário (janeiro = 0)

		// Verifica se o primeiro dia do mês é uma segunda-feira
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek != Calendar.MONDAY) {
			// Se não for, adiciona os dias restantes da primeira semana separadamente
			int daysUntilMonday = 9 - dayOfWeek; // 9 porque estamos considerando que segunda é o segundo dia da semana
													// (e não o primeiro)
			List<Date> firstWeekDays = new ArrayList<>();
			for (int i = 0; i < daysUntilMonday; i++) {
				firstWeekDays.add(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			monthWeekDaysList.add(firstWeekDays);
		}

		// Itera pelas semanas do mês, adicionando os dias da semana a cada semana
		while (cal.get(Calendar.MONTH) == month - 1) {
			List<Date> weekDays = new ArrayList<>();
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
			List<Date> lastWeekDays = new ArrayList<>();
			cal.add(Calendar.DAY_OF_MONTH, -daysFromMonday);
			for (int i = 0; i < daysFromMonday; i++) {
				lastWeekDays.add(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			monthWeekDaysList.add(lastWeekDays);
		}

		return monthWeekDaysList;
	}

	public static List<Date> getMonthDates(int year, int month) {
		// Cria uma lista para armazenar as datas do mês
		List<Date> monthDatesList = new ArrayList<>();

		// Cria um calendário para representar o primeiro dia do mês
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1); // subtrai 1 do mês para ajustar ao formato de calendário (janeiro = 0)

		// Adiciona as datas do mês à lista
		while (cal.get(Calendar.MONTH) == month - 1) {
			monthDatesList.add(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}

		return monthDatesList;
	}

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

	public void aulasFrame() {

		headerPanel.removeAll();
		contentPanel.removeAll();
		footerPanel.removeAll();
		revalidate();
		repaint();

		JButton nextButton2 = new JButton("Next Day");
		footerPanel.add(nextButton2, BorderLayout.CENTER);

		final JLabel aulaLabel = new JLabel();
		contentPanel.add(aulaLabel);
		aulas = adicionar(aulas);
		exibirAula(aulas.get(currentAulaIndex));

		nextButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentAulaIndex++;
				if (currentAulaIndex < aulas.size()) {
					System.out.println(currentAulaIndex);
					exibirAula(aulas.get(currentAulaIndex));
				} else {
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
					populateSchedule(scheduleList, currentWeekDays);
				}
			}
		});
	}

	private void exibirAula(List<String> aula) {
		System.out.println("Entras aqui!");
		contentPanel.removeAll();
		contentPanel.add(new JLabel("Dia da semana: "));
		contentPanel.add(new JLabel(aula.get(0)));
		System.out.println(aula.get(0));
		contentPanel.add(new JLabel("Hora de Inicio: "));
		contentPanel.add(new JLabel(aula.get(1)));
		contentPanel.add(new JLabel("Data:"));
		contentPanel.add(new JLabel(aula.get(2)));
		contentPanel.add(new JLabel("Unidade Curricular:"));
		contentPanel.add(new JLabel(aula.get(3)));
		revalidate();
		repaint();
	}

	public static void main(String[] args) throws IOException {
		Horario h = new Horario();
		h.carregarAulasDeArquivoCSV("/Users/tomasrosa/Desktop/horario_exemplo_2.csv");
		List<List<Date>> d = getMonthWeekDays(2022, 11);
		DaySchedule schedulePanel = new DaySchedule(h.getAulas(), d);

		JFrame frame = new JFrame("Horário das Aulas");
		frame.getContentPane().add(schedulePanel);

		// Configura o tamanho do JFrame e o torna visível
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public static List<List<String>> adicionar(List<List<String>> aulas) {

		// Cria duas listas vazias para armazenar as aulas de seg e ter
		List<List<String>> aulasSeg = new ArrayList<>();
		List<List<String>> aulasTer = new ArrayList<>();
		List<List<String>> aulasQua = new ArrayList<>();
		List<List<String>> aulasQui = new ArrayList<>();
		List<List<String>> aulasSex = new ArrayList<>();

		// Percorre a lista de aulas e adiciona as aulas de seg e ter às suas
		// respectivas listas
		for (List<String> aula : aulas) {
			System.out.println(aula.get(0));
			if (aula.get(0).equals("Seg")) {
				aulasSeg.add(aula);
			} else if (aula.get(0).equals("Ter")) {
				aulasTer.add(aula);
			} else if (aula.get(0).equals("Qua")) {
				System.out.println("entras aqui?????");
				aulasQua.add(aula);
			} else if (aula.get(0).equals("Qui")) {
				aulasQui.add(aula);
			} else if (aula.get(0).equals("Sex")) {
				aulasSex.add(aula);
			}
		}

		// Cria uma nova lista que vai conter todas as aulas de seg seguidas por todas
		// as aulas de ter
		List<List<String>> aulasSegTer = new ArrayList<>();
		aulasSegTer.addAll(aulasSeg);
		aulasSegTer.addAll(aulasTer);
		aulasSegTer.addAll(aulasQua);
		aulasSegTer.addAll(aulasQui);
		aulasSegTer.addAll(aulasSex);

		// Retorna a lista de aulas de seg e ter
		return aulasSegTer;

	}

}
