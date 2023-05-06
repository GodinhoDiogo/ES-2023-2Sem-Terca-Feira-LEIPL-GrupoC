package WebSchedule;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import modules.Horario;
import modules.Schedule;

public class MonthSchedule extends JPanel {
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
	private JButton monthButton;
	JButton button = new JButton("Overlapping Classes");
	final List<List<String>> overlappingClasses = new ArrayList<>();
	public MonthSchedule(final List<Schedule> scheduleList, final List<List<Date>> novemberWeekDaysList) {
		this.scheduleList = scheduleList;
		this.currentWeekDays = novemberWeekDaysList.get(0);
		this.currentWeekIndex = 0;
		this.month = 11;

		setLayout(new BorderLayout());
		monthButton = new JButton("View Month");
		// Cria o painel do cabeçalho
		headerPanel = new JPanel(new BorderLayout());
		titleLabel = new JLabel("Horário das Aulas");
		headerPanel.add(titleLabel, BorderLayout.CENTER);
		add(headerPanel, BorderLayout.NORTH);
		headerPanel.add(monthButton, BorderLayout.EAST);
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

		// footerPanel.add(monthButton, BorderLayout.CENTER);

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

		monthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				monthFrame();

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
	    // Add the days of the week to the first row of the panel
	    contentPanel.add(new JLabel(""));
	    for (String day : result) {
	        contentPanel.add(new JLabel(day));
	    }

	    String[] horarios = { "08:00:00","09:30:00","11:00:00","13:00:00", "14:30:00", "16:00:00", "17:30:00", "18:00:00" };
	    final String[] dates = convert2(result);

	    for (final String horario : horarios) {
	        JLabel label = new JLabel(horario);
	        label.setHorizontalAlignment(JLabel.CENTER);
	        label.setVerticalAlignment(JLabel.CENTER);
	        contentPanel.add(label);
	        for (int j = 0; j < 7; j++) {
	        	final int i=j;
	            JLabel cellLabel = new JLabel();
	            cellLabel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); // add border
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
	                    lista.add(schedule.getInscritosNoTurno());
	                    lista.add(schedule.getLotacaoSala());
	                    aulas.add(lista);
	                }
	            }
	            int count = 0;
	            for (List<String> aula : aulas) {
	                if (aula.get(1).equals(horario) && aula.get(0).equals(getDiaSemana(j)) && aula.get(2).equals(dates[j])) {
	                    count++;
	                }
	            }
	            for(List<String> aula :aulas) {
	            	if(aula.get(4).equals("") || aula.get(5).equals("")) {
	            	}else {
	            		if(Integer.parseInt(aula.get(4)) > Integer.parseInt(aula.get(5))) {
	            			System.out.println(aula.get(4) + " ola" + aula.get(5));
		            		 cellLabel.setBorder(BorderFactory.createLineBorder(new Color(255,0,0)));
		            	}
	            	}
	            	
	            }
	            if (count >= 2) {
	                JButton seeClashesButton = new JButton("Aula sobreposta");
	                seeClashesButton.addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        JFrame clashPopup = new JFrame("Clashed Classes");
	                        clashPopup.setSize(300, 200);
	                        clashPopup.setLocationRelativeTo(null);
	                        JTextArea clashTextArea = new JTextArea();
	                        for (List<String> aula : aulas) {
	                            if (aula.get(1).equals(horario) && aula.get(0).equals(getDiaSemana(i)) && aula.get(2).equals(dates[i])) {
	                                clashTextArea.append(aula.get(3) + "\n");
	                            }
	                        }
	                        clashPopup.add(new JScrollPane(clashTextArea));
	                        clashPopup.setVisible(true);
	                    }
	                });
	                cellLabel.setLayout(new BorderLayout());
	                cellLabel.add(seeClashesButton, BorderLayout.NORTH);
	            }
	            cellLabel.setHorizontalAlignment(JLabel.CENTER);
	            cellLabel.setVerticalAlignment(JLabel.CENTER);
	            contentPanel.add(cellLabel);
	        }
	    }
	    contentPanel.revalidate(); // Update the user interface
	    contentPanel.repaint();
	}

//	private void populateSchedule(List<Schedule> schedules, List<Date> currentWeekDays) {
//	    aulas.clear();
//	    contentPanel.removeAll();
//	    String[] result = convert(currentWeekDays); // o result sao os dias da semana
//	    // Adiciona os dias da semana na primeira linha do painel
//	    contentPanel.add(new JLabel(""));
//	    contentPanel.add(new JLabel(result[0]));
//	    contentPanel.add(new JLabel(result[1]));
//	    contentPanel.add(new JLabel(result[2]));
//	    contentPanel.add(new JLabel(result[3]));
//	    contentPanel.add(new JLabel(result[4]));
//	    contentPanel.add(new JLabel(result[5]));
//	    contentPanel.add(new JLabel(result[6]));
//
//	    String[] horarios = { "08:00:00","09:30:00","11:00:00","13:00:00", "14:30:00", "16:00:00", "17:30:00", "18:00:00" };
//	    String[] dates = convert2(result);
//
//	    for (String horario : horarios) {
//	        JLabel label = new JLabel(horario);
//	        label.setHorizontalAlignment(JLabel.CENTER);
//	        label.setVerticalAlignment(JLabel.CENTER);
//	        contentPanel.add(label);
//	        for (int j = 0; j < 7; j++) {
//	            JLabel cellLabel = new JLabel();
//	            cellLabel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); // add border
//	            JTextField clashTextField = new JTextField("");
//	            for (Schedule schedule : schedules) {
//	                System.out.println(schedule.getHorarioInicioAula() + " " + schedule.getDiaSemana() + " " + schedule.getDataAula());
//	                if (schedule.getHorarioInicioAula().equals(horario)
//	                        && schedule.getDiaSemana().equals(getDiaSemana(j))
//	                        && schedule.getDataAula().equals(dates[j])) {
//
//	                    cellLabel.setText(schedule.getUnidadeCurricular());
//	                    List<String> lista = new ArrayList<String>();
//	                    lista.add(schedule.getDiaSemana());
//	                    lista.add(schedule.getHorarioInicioAula());
//	                    lista.add(schedule.getDataAula());
//	                    lista.add(schedule.getUnidadeCurricular());
//	                    aulas.add(lista);
//	                    System.out.println("Nao me estas a entrar � aqui pois nao?");
//	                }
//	            }
//	            int count = 0;
//	            for (List<String> aula : aulas) {
//	                if (aula.get(1).equals(horario) && aula.get(0).equals(getDiaSemana(j)) && aula.get(2).equals(dates[j])) {
//	                    count++;
//	                }
//	            }
//	            if (count >= 2) {
//	                clashTextField.setText("Clash!");
//	            }
//	            clashTextField.setEditable(false);
//	            cellLabel.setLayout(new BorderLayout());
//	            cellLabel.add(clashTextField, BorderLayout.NORTH);
//	            cellLabel.setHorizontalAlignment(JLabel.CENTER);
//	            cellLabel.setVerticalAlignment(JLabel.CENTER);
//	            contentPanel.add(cellLabel);
//	        }
//	    }
//	    contentPanel.revalidate(); // Atualiza a interface do usu�rio
//	    contentPanel.repaint();
//	}

//	private void populateSchedule(List<Schedule> schedules, List<Date> currentWeekDays) {
//	    aulas.clear();
//	    contentPanel.removeAll();
//	    String[] result = convert(currentWeekDays); // o result sao os dias da semana
//	    // Adiciona os dias da semana na primeira linha do painel
//	    contentPanel.add(new JLabel(""));
//	    contentPanel.add(new JLabel(result[0]));
//	    contentPanel.add(new JLabel(result[1]));
//	    contentPanel.add(new JLabel(result[2]));
//	    contentPanel.add(new JLabel(result[3]));
//	    contentPanel.add(new JLabel(result[4]));
//	    contentPanel.add(new JLabel(result[5]));
//	    contentPanel.add(new JLabel(result[6]));
//
//	    String[] horarios = { "08:00:00","09:30:00","11:00:00","13:00:00", "14:30:00", "16:00:00", "17:30:00", "18:00:00" };
//	    String[] dates = convert2(result);
//
//	    for (String horario : horarios) {
//	        JLabel label = new JLabel(horario);
//	        label.setHorizontalAlignment(JLabel.CENTER);
//	        label.setVerticalAlignment(JLabel.CENTER);
//	        contentPanel.add(label);
//	        for (int j = 0; j < 7; j++) {
//	            JLabel cellLabel = new JLabel();
//	            cellLabel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); // add border
//	            for (Schedule schedule : schedules) {
//	                System.out.println(schedule.getHorarioInicioAula() + " " + schedule.getDiaSemana() + " " + schedule.getDataAula());
//	                if (schedule.getHorarioInicioAula().equals(horario)
//	                        && schedule.getDiaSemana().equals(getDiaSemana(j))
//	                        && schedule.getDataAula().equals(dates[j])) {
//
//	                    cellLabel.setText(schedule.getUnidadeCurricular());
//	                    List<String> lista = new ArrayList<String>();
//	                    lista.add(schedule.getDiaSemana());
//	                    lista.add(schedule.getHorarioInicioAula());
//	                    lista.add(schedule.getDataAula());
//	                    lista.add(schedule.getUnidadeCurricular());
//	                    aulas.add(lista);
//	                    System.out.println("Nao me estas a entrar � aqui pois nao?");
//	                }
//	            }
//	            cellLabel.setHorizontalAlignment(JLabel.CENTER);
//	            cellLabel.setVerticalAlignment(JLabel.CENTER);
//	            contentPanel.add(cellLabel);
//	        }
//	    }
//	    contentPanel.revalidate(); // Atualiza a interface do usu�rio
//	    contentPanel.repaint();
//	}


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

	private void monthFrame() {
		// Remove existing components
		headerPanel.removeAll();
		contentPanel.removeAll();
		footerPanel.removeAll();
		revalidate();
		repaint();

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
		button.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JDialog dialog = new JDialog();
		        dialog.setTitle("Overlapping Classes");
		        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		        JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		        for (List<String> overlappingClass : overlappingClasses) {
		            JLabel label = new JLabel("Dia: " + overlappingClass.get(2) + " Hor�rio: "+ overlappingClass.get(2) + " UC: "+ overlappingClass.get(3));
//		            currentClass.add(schedule.getDiaSemana());
//		            currentClass.add(schedule.getHorarioInicioAula());
//		            currentClass.add(schedule.getDataAula());
//		            currentClass.add(schedule.getUnidadeCurricular());
		            panel.add(label);
		        }

		        dialog.getContentPane().add(panel);
		        dialog.pack();
		        dialog.setLocationRelativeTo(null);
		        dialog.setVisible(true);
		    }
		});
		
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
		footerPanel.add(button);

		// Repaint view
		revalidate();
		repaint();
	}
	
	public static void initializeSchdule(List<Schedule> list) {
		Horario h = new Horario();
		try {
			h.carregarAulasDeArquivoCSV("C:\\Users\\nanor\\OneDrive\\Ambiente de Trabalho\\schedules.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<List<Date>> d = getMonthWeekDays(2022, 11);
		MonthSchedule schedulePanel = new MonthSchedule(list, d);

		JFrame frame = new JFrame("Horário das Aulas");
		frame.getContentPane().add(schedulePanel);

		// Configura o tamanho do JFrame e o torna visível
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		Horario h = new Horario();
		h.carregarAulasDeArquivoCSV("C:\\Users\\nanor\\OneDrive\\Ambiente de Trabalho\\schedules.csv");
		List<List<Date>> d = getMonthWeekDays(2022, 11);
		MonthSchedule schedulePanel = new MonthSchedule(h.getAulas(), d);

		JFrame frame = new JFrame("Horário das Aulas");
		frame.getContentPane().add(schedulePanel);

		// Configura o tamanho do JFrame e o torna visível
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
//
	private void populateSchedule2(List<Schedule> schedules, List<Date> currentMonthDays) {
		aulas.clear();
//		contentPanel.removeAll();
		String[] result = convert(currentMonthDays); // o result sao os dias da semana
		// Adiciona os dias da semana na primeira linha do painel
		contentPanel.add(new JLabel(""));
		contentPanel.add(new JLabel(result[0]));
		contentPanel.add(new JLabel(result[1]));
		contentPanel.add(new JLabel(result[2]));
		contentPanel.add(new JLabel(result[3]));
		contentPanel.add(new JLabel(result[4]));
		contentPanel.add(new JLabel(result[5]));
		contentPanel.add(new JLabel(result[6]));

		String[] horarios = { "08:00:00","09:30:00","11:00:00","13:00:00", "14:30:00", "16:00:00", "17:30:00", "18:00:00" };
		String[] dates = convert2(result);

		for (String horario : horarios) {
			JLabel label = new JLabel(horario);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
			contentPanel.add(label);
			for (int j = 0; j < 7; j++) {
			    JLabel cellLabel = new JLabel();
			    cellLabel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); // add border

			     // new line

			    for (Schedule schedule : schedules) {
			        if (schedule.getHorarioInicioAula().equals(horario)
			            && schedule.getDiaSemana().equals(getDiaSemana(j))
			            && schedule.getDataAula().equals(dates[j])) {

			            List<String> currentClass = new ArrayList<>(); // new line
			            currentClass.add(schedule.getDiaSemana());
			            currentClass.add(schedule.getHorarioInicioAula());
			            currentClass.add(schedule.getDataAula());
			            currentClass.add(schedule.getUnidadeCurricular());

			            boolean isOverlapping = false;
			            for (List<String> overlappingClass : overlappingClasses) {
			                if (overlappingClass.get(0).equals(currentClass.get(0))
			                    && overlappingClass.get(1).equals(currentClass.get(1))
			                    && overlappingClass.get(2).equals(currentClass.get(2))) {
			                    overlappingClass.add(currentClass.get(3));
			                    isOverlapping = true;
			                    break;
			                }
			            }

			            if (!isOverlapping) {
			                cellLabel.setText(currentClass.get(3));
			                overlappingClasses.add(currentClass);
			            }else {
			            	System.out.println(schedule.getCurso() + " " + schedule.getDataAula() + " "+ schedule.getDiaSemana());
			            
			            	JButton button = new JButton("Overlapping Classes");
			            	button.addActionListener(new ActionListener() {
			            	    @Override
			            	    public void actionPerformed(ActionEvent e) {
			            	        JDialog dialog = new JDialog();
			            	        dialog.setTitle("Overlapping Classes");
			            	        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			            	        JPanel panel = new JPanel();
			            	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

			            	        for (List<String> overlappingClass : overlappingClasses) {
			            	            JLabel label = new JLabel(overlappingClass.get(3));
			            	            panel.add(label);
			            	        }

			            	        dialog.getContentPane().add(panel);
			            	        dialog.pack();
			            	        dialog.setLocationRelativeTo(null);
			            	        dialog.setVisible(true);
			            	    }
			            	});
			            	cellLabel.add(button);

			            
			        }
			        }
			    }

			    if (overlappingClasses.size() > 1) { // new line
			        StringBuilder tooltipText = new StringBuilder(); // new line
			        tooltipText.append("<html>"); // new line
			        for (List<String> overlappingClass : overlappingClasses) { // new line
			            tooltipText.append(overlappingClass.get(3)).append("<br>"); // new line
			        }
			        tooltipText.append("</html>"); // new line
			        cellLabel.setToolTipText(tooltipText.toString()); // new line
			    } // new line

			    cellLabel.setHorizontalAlignment(JLabel.CENTER);
			    cellLabel.setVerticalAlignment(JLabel.CENTER);
			    contentPanel.add(cellLabel);
			}

		}
		contentPanel.revalidate(); // Atualiza a interface do usuário
		contentPanel.repaint();
	}
	
	

//	private void populateSchedule2(List<Schedule> schedules, List<Date> currentMonthDays, JPanel parentPanel) {
//	    aulas.clear();
//	    contentPanel.removeAll();
//	    String[] result = convert(currentMonthDays); // o result sao os dias da semana
//	    // Adiciona os dias da semana na primeira linha do painel
//	    contentPanel.add(new JLabel(""));
//	    contentPanel.add(new JLabel(result[0]));
//	    contentPanel.add(new JLabel(result[1]));
//	    contentPanel.add(new JLabel(result[2]));
//	    contentPanel.add(new JLabel(result[3]));
//	    contentPanel.add(new JLabel(result[4]));
//	    contentPanel.add(new JLabel(result[5]));
//	    contentPanel.add(new JLabel(result[6]));
//
//	    String[] horarios = { "08:00:00", "09:30:00", "11:00:00", "13:00:00", "14:30:00", "16:00:00", "17:30:00", "18:00:00" };
//	    String[] dates = convert2(result);
//
//	    for (String horario : horarios) {
//	        JLabel label = new JLabel(horario);
//	        label.setHorizontalAlignment(JLabel.CENTER);
//	        label.setVerticalAlignment(JLabel.CENTER);
//	        contentPanel.add(label);
//	        for (int j = 0; j < 7; j++) {
//	            JLabel cellLabel = new JLabel();
//	            cellLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0))); // add border
//
//	            for (Schedule schedule : schedules) {
//	                if (schedule.getHorarioInicioAula().equals(horario)
//	                        && schedule.getDiaSemana().equals(getDiaSemana(j))
//	                        && schedule.getDataAula().equals(dates[j])) {
//
//	                    cellLabel.setText(schedule.getUnidadeCurricular());
//	                    List<String> lista = new ArrayList<String>();
//	                    lista.add(schedule.getDiaSemana());
//	                    lista.add(schedule.getHorarioInicioAula());
//	                    lista.add(schedule.getDataAula());
//	                    lista.add(schedule.getUnidadeCurricular());
//	                    aulas.add(lista);
//	                }
//	            }
//	            cellLabel.setHorizontalAlignment(JLabel.CENTER);
//	            cellLabel.setVerticalAlignment(JLabel.CENTER);
//	            contentPanel.add(cellLabel);
//	        }
//	    }
//	    contentPanel.revalidate(); // Atualiza a interface do usu�rio
//	    contentPanel.repaint();
//
//	    // Create the button panel and add the "See clashes" button
//	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//	    JButton seeClashesButton = new JButton("See clashes");
//	    seeClashesButton.addActionListener(new ActionListener() {
//	        @Override
//	        public void actionPerformed(ActionEvent e) {
//	            findAndDisplayClashes(aulas);
//	        }
//	    });
//	    buttonPanel.add(seeClashesButton);
//
//	    // Add the button panel to the bottom of the parent container
//	    parentPanel.add(buttonPanel, BorderLayout.SOUTH);
//	}






//	private void populateSchedule2(List<Schedule> schedules, List<Date> currentWeekDays) {
//	    aulas.clear();
//	    contentPanel.removeAll();
//	    String[] result = convert(currentWeekDays);
//	    contentPanel.add(new JLabel(""));
//	    for (String day : result) {
//	        contentPanel.add(new JLabel(day));
//	    }
//
//	    String[] horarios = { "08:00:00","09:30:00","11:00:00","13:00:00", "14:30:00", "16:00:00", "17:30:00", "18:00:00" };
//	    String[] dates = convert2(result);
//
//	    for (String horario : horarios) {
//	        JLabel label = new JLabel(horario);
//	        label.setHorizontalAlignment(JLabel.CENTER);
//	        label.setVerticalAlignment(JLabel.CENTER);
//	        contentPanel.add(label);
//	        for (int j = 0; j < 7; j++) {
//	            JLabel cellLabel = new JLabel();
//	            cellLabel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); // add border
//	            for (Schedule schedule : schedules) {
//	                if (schedule.getHorarioInicioAula().equals(horario)
//	                        && schedule.getDiaSemana().equals(getDiaSemana(j))
//	                        && schedule.getDataAula().equals(dates[j])) {
//
//	                    cellLabel.setText(schedule.getUnidadeCurricular());
//	                    List<String> lista = new ArrayList<String>();
//	                    lista.add(schedule.getDiaSemana());
//	                    lista.add(schedule.getHorarioInicioAula());
//	                    lista.add(schedule.getDataAula());
//	                    lista.add(schedule.getUnidadeCurricular());
//	                    aulas.add(lista);
//	                }
//	            }
//	            cellLabel.setHorizontalAlignment(JLabel.CENTER);
//	            cellLabel.setVerticalAlignment(JLabel.CENTER);
//	            contentPanel.add(cellLabel);
//	        }
//	    }
//
//	    JButton seeClashesButton = new JButton("See clashes");
//	    seeClashesButton.addActionListener(new ActionListener() {
//	        @Override
//	        public void actionPerformed(ActionEvent e) {
//	            JFrame clashPopup = new JFrame("Clashed Classes");
//	            clashPopup.setSize(300, 200);
//	            clashPopup.setLocationRelativeTo(null);
//	            JTextArea clashTextArea = new JTextArea();
//
//	            boolean clashesFound = false;
//	            for (int i = 0; i < aulas.size() - 1; i++) {
//	                List<String> aula1 = aulas.get(i);
//	                for (int j = i + 1; j < aulas.size(); j++) {
//	                    List<String> aula2 = aulas.get(j);
//	                    if (aula1.get(0).equals(aula2.get(0)) && aula1.get(1).equals(aula2.get(1)) && aula1.get(2).equals(aula2.get(2))) {
//	                        clashesFound = true;
//	                        clashTextArea.append(aula1.get(3) + " and " + aula2.get(3) + " on " + aula1.get(2) + "\n");
//	                    }
//	                }
//	            }
//
//	            if (!clashesFound) {
//	                clashTextArea.append("No clashes found.");
//	            }
//
//	            clashPopup.add(new JScrollPane(clashTextArea));
//	            clashPopup.setVisible(true);
//	        }
//	    });
//
//	    contentPanel.add(seeClashesButton);
//	    contentPanel.revalidate();
//	    contentPanel.repaint();
//	}

}
