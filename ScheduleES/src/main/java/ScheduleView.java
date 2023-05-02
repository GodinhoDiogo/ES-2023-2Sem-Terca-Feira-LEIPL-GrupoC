import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import modules.Schedule;
import org.apache.commons.lang3.time.DateUtils;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleView extends JFrame {
    private final JTable table;
    private final DefaultTableModel model;

    public ScheduleView(List<Schedule> schedule) {
        // Configurar a janela
        setTitle("Horário");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar a tabela
        String[] headers = {"Curso", "UC", "Turno", "Turma", "Dia", "Início", "Fim", "Sala", "Lotação"};
        model = new DefaultTableModel(headers, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Preencher a tabela com os dados do horário
        for (Schedule aula : schedule) {
            Object[] row = {aula.getCurso(), aula.getUnidadeCurricular(), aula.getTurno(), aula.getTurma(),
                    aula.getDiaSemana(), aula.getHorarioInicioAula(), aula.getHorarioFimAula(),
                    aula.getSalaAtribuida(), aula.getLotacaoSala()};
            model.addRow(row);
        }

        // Mostrar a janela
        setVisible(true);
    }
    public void showDaySchedule(List<Schedule> scheduleList) {
        String input = JOptionPane.showInputDialog(null, "Insira a data (dd/MM/yyyy) para exibir a agenda diária:");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date selectedDate;
        try {
            selectedDate = sdf.parse(input);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Data inválida! Use o formato dd/MM/yyyy");
            return;
        }
        List<Schedule> dailySchedule = new ArrayList<Schedule>();
        for (Schedule schedule : scheduleList) {
            if (schedule.getDataAula().equals(selectedDate)) {
                dailySchedule.add(schedule);
            }
        }
        if (dailySchedule.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há aulas agendadas para a data informada.");
        } else {
            StringBuilder message = new StringBuilder();
            message.append("Aulas agendadas para o dia " + sdf.format(selectedDate) + ":\n\n");
            for (Schedule schedule : dailySchedule) {
                message.append(schedule.getUnidadeCurricular() + " - " + schedule.getHorarioInicioAula() + " - Sala " + schedule.getSalaAtribuida() + "\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
        }
    }

    public void showWeekSchedule(List<Schedule> scheduleList) {
        String input = JOptionPane.showInputDialog(null, "Insira a data (dd/MM/yyyy) de início da semana:");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate;
        try {
            startDate = sdf.parse(input);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Data inválida! Use o formato dd/MM/yyyy");
            return;
        }
        List<Schedule> weeklySchedule = new ArrayList<Schedule>();
        for (Schedule schedule : scheduleList) {
            if (schedule.getDataAula().after(startDate) && schedule.getDataAula().before(DateUtils.addDays(startDate, 7))) {
                weeklySchedule.add(schedule);
            }
        }
        if (weeklySchedule.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há aulas agendadas para a semana informada.");
        } else {
            StringBuilder message = new StringBuilder();
            message.append("Aulas agendadas para a semana de " + sdf.format(startDate) + ":\n\n");
            for (Schedule schedule : weeklySchedule) {
                message.append(schedule.getUnidadeCurricular() + " - " + schedule.getDiaSemana() + " - " + schedule.getHorarioInicioAula() + " - Sala " + schedule.getSalaAtribuida() + "\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
        }
    }
    public void showMonthSchedule(List<Schedule> scheduleList) {
        String input = JOptionPane.showInputDialog(null, "Insira o mês e ano desejados (MM/yyyy):");
        SimpleDateFormat inputSdf = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat outputSdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate;
        try {
            startDate = inputSdf.parse(input);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Data inválida! Use o formato MM/yyyy");
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int numDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, 1, 0, 0, 0);
        Date firstDayOfMonth = calendar.getTime();
        calendar.set(year, month, numDaysInMonth, 23, 59, 59);
        Date lastDayOfMonth = calendar.getTime();
        List<Schedule> monthlySchedule = new ArrayList<Schedule>();
        for (Schedule schedule : scheduleList) {
            if (schedule.getDataAula().after(firstDayOfMonth) && schedule.getDataAula().before(lastDayOfMonth)) {
                monthlySchedule.add(schedule);
            }
        }
        if (monthlySchedule.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há aulas agendadas para o mês informado.");
        } else {
            StringBuilder message = new StringBuilder();
            message.append("Aulas agendadas para o mês de " + outputSdf.format(startDate) + ":\n\n");
            for (Schedule schedule : monthlySchedule) {
                message.append(schedule.getUnidadeCurricular() + " - " + outputSdf.format(schedule.getDataAula()) + " - " + schedule.getDiaSemana() + " - " + schedule.getHorarioInicioAula() + " - Sala " + schedule.getSalaAtribuida() + "\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
        }
    }


//    public static void main(String[] args) {
//    	
//    	ScheduleView s = new ScheduleView();
//    }
}
