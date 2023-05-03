import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import modules.Schedule;
import javax.swing.JOptionPane;

public class ScheduleController {
    private ScheduleView view;

    public ScheduleController(ScheduleView view) {
        this.view = view;
    }

    public void loadScheduleFromFile(String filename) {
        List<Schedule> scheduleList = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                throw new IOException("Arquivo não encontrado: " + filename);
            }
            if (filename.endsWith(".json")) {
                scheduleList = ScheduleJSONFileReader.readFromFile(file);
            } else if (filename.endsWith(".csv")) {
                scheduleList = ScheduleCSVFileReader.readFromFile(filename);
            } else {
                throw new IOException("Formato de arquivo não suportado: " + filename);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar arquivo: " + e.getMessage());
            return;
        }

        view.showDaySchedule(scheduleList);
        view.showWeekSchedule(scheduleList);
        view.showMonthSchedule(scheduleList);
    }

    public void filterScheduleByDate(String dateString, List<Schedule> scheduleList) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse(dateString);
            view.showDaySchedule(ScheduleUtils.filterByDay(date, scheduleList));
            view.showWeekSchedule(ScheduleUtils.filterByWeek(date, scheduleList));
            view.showMonthSchedule(ScheduleUtils.filterByMonth(date, scheduleList));
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Data inválida: " + dateString);
        }
    }
}
