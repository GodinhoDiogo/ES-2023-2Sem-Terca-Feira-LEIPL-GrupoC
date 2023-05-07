package WebSchedule;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import modules.Schedule;
import javax.swing.JOptionPane;

import converters.JsonToCsvConverter;

public class ScheduleController {
   

    public void loadScheduleFromFile(String filename) {
        List<Schedule> scheduleList = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                throw new IOException("Arquivo n�o encontrado: " + filename);
            }
            if (filename.endsWith(".json")) {
                scheduleList = ScheduleJSONFileReader.readFromFile(file);
            } else if (filename.endsWith(".csv")) {
                scheduleList = ScheduleCSVFileReader.readFromFile(filename);
            } else {
                throw new IOException("Formato de arquivo n�o suportado: " + filename);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar arquivo: " + e.getMessage());
            return;
        }

       
    }
}
