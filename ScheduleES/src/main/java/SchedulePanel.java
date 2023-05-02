import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modules.Schedule;

public class SchedulePanel extends JPanel {
    private JPanel headerPanel;
    private JLabel titleLabel;
    private JPanel contentPanel;
    private JPanel footerPanel;
    private JButton previousButton;
    private JButton nextButton;
    private List<Schedule> scheduleList;
    private int currentWeekIndex;

    public SchedulePanel(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
        this.currentWeekIndex = 0;

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
//        previousButton = new JButton("<<");
//        footerPanel.add(previousButton, BorderLayout.WEST);
//        nextButton = new JButton(">>");
//        footerPanel.add(nextButton, BorderLayout.EAST);
//        add(footerPanel, BorderLayout.SOUTH);
//
//        // Preenche o painel do conteúdo com os dados do horário de aulas
//        populateSchedule();
//
//        // Adiciona eventos de clique nos botões de navegação
//        previousButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (currentWeekIndex > 0) {
//                    currentWeekIndex--;
//                    populateSchedule();
//                }
//            }
//        });
//
//        nextButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (currentWeekIndex < scheduleList.size() - 1) {
//                    currentWeekIndex++;
//                    populateSchedule();
//                }
//            }
//        });
    }

    private void populateSchedule() {
        // Adiciona os dias da semana na primeira linha do painel
        contentPanel.add(new JLabel(""));
        contentPanel.add(new JLabel("Segunda"));
        contentPanel.add(new JLabel("Terça"));
        contentPanel.add(new JLabel("Quarta"));
        contentPanel.add(new JLabel("Quinta"));
        contentPanel.add(new JLabel("Sexta"));
        contentPanel.add(new JLabel("Sábado"));
        contentPanel.add(new JLabel("Domingo"));

        // Adiciona as aulas do horário nas linhas seguintes do painel
        for (int i = 0; i < 8; i++) {
            contentPanel.add(new JLabel("Aula " + (i + 1)));

            for (int j = 0; j < 7; j++) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);

                Schedule schedule = scheduleList.get(j);
                List<Schedule> schedulesOfDay = getScheduleOfDay(schedule.getDiaSemana());

                if (i < schedulesOfDay.size()) {
                    Schedule scheduleOfDay = schedulesOfDay.get(i);
                    label.setText(scheduleOfDay.getUnidadeCurricular() + " (" + scheduleOfDay.getTurno() + ")");
                }

                contentPanel.add(label);
            }
        }
    }

    private List<Schedule> getScheduleOfDay(String dayOfWeek) {
        List<Schedule> schedulesOfDay = new ArrayList<Schedule>();
        for (Schedule schedule : scheduleList) {
        	if (schedule.getDiaSemana().equals(dayOfWeek)) {
        		schedulesOfDay.add(schedule);
        		}
        		}
        		return schedulesOfDay;
        		}
        		}

