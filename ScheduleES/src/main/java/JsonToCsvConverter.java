

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import com.google.gson.Gson;

import sure.Schedule;
import sure.ScheduleList;

public class JsonToCsvConverter {

    public static void main(String[] args) throws IOException {
        // Specify the path to your JSON file containing the list of schedules
        String jsonFilePath = "/Users/tomascarvalho/Desktop/schedules.json";

        // Specify the path to your output CSV file
        String csvFilePath = "/Users/tomascarvalho/Desktop/schedules.csv";

        // Create a reader for the JSON file
        Reader jsonReader = new FileReader(jsonFilePath);

        // Parse the JSON file into a list of Schedule objects
        Gson gson = new Gson();
        ScheduleList schedules = gson.fromJson(jsonReader, ScheduleList.class);

        // Create a writer for the CSV file
        Writer csvWriter = new FileWriter(csvFilePath);

        // Create a CSVPrinter using Apache Commons CSV with ";" as the delimiter
        CSVPrinter csvPrinter = new CSVPrinter(csvWriter, CSVFormat.DEFAULT.withDelimiter(','));

        // Write the header row to the CSV file with attribute names
        csvPrinter.printRecord("Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no Turno", "Hor�rio Fim Aula",
                "Dia Semana", "Hor�rio In�cio Aula", "Data Aula", "Sala Atribu�da", "Lota��o Sala");

        // Write the Schedule objects to the CSV file
        for (Schedule schedule : schedules.getSchedules()) {
            csvPrinter.printRecord(schedule.getCurso(), schedule.getUnidadeCurricular(), schedule.getTurno(),
                    schedule.getTurma(), schedule.getInscritosNoTurno(), schedule.getHorarioFimAula(),
                    schedule.getDiaSemana(), schedule.getHorarioInicioAula(), schedule.getDataAula(),
                    schedule.getSalaAtribuida(), schedule.getLotacaoSala());
        }

        // Close the CSVPrinter and Writer
        csvPrinter.close();
        csvWriter.close();
    }
}