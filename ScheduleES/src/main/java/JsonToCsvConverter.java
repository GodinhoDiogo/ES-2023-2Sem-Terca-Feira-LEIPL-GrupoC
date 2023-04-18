
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import com.google.gson.Gson;

import modules.Schedule;
import modules.ScheduleList;

public class JsonToCsvConverter {
	public File JsonToCsvConverted(File file, String path) throws IOException {

        // Specify the path to your output CSV file
        String csvFilePath = path;

        // Create a reader for the JSON file
        Reader jsonReader = new FileReader(file);

        // Parse the JSON file into a list of Schedule objects
        Gson gson = new Gson();
        ScheduleList schedules = gson.fromJson(jsonReader, ScheduleList.class);

        // Create a writer for the CSV file
        Writer csvWriter = new FileWriter(csvFilePath);

        // Get the attribute names of the Schedule class
        String[] headers = {"curso", "unidadeCurricular", "turno", "turma", "inscritosNoTurno", "horarioFimAula",
                            "diaSemana", "horarioInicioAula", "dataAula", "salaAtribuida", "lotacaoSala"};

        // Create a CSVPrinter using Apache Commons CSV with ";" as the delimiter and the attribute names as headers
        CSVPrinter csvPrinter = new CSVPrinter(csvWriter, CSVFormat.DEFAULT.withHeader(headers).withDelimiter(';'));

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
		return file;
	}
}
