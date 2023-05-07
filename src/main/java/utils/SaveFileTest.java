/**

The utils package contains utility classes for various tasks.
*/
package utils;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.google.gson.Gson;

import modules.Schedule;
import modules.ScheduleList;

/**

The SaveFile class provides methods for saving data to files in JSON or CSV format.
*/
public class SaveFileTest {

/**

Saves a ScheduleList object to a JSON file.
@param schedules the ScheduleList object to be saved
@param path the path to the JSON file to be written
*/
public static void saveFileJson(ScheduleList schedules, String path) {
Gson gson = new Gson();
String json = gson.toJson(schedules);
try {
FileWriter writer = new FileWriter(path);
writer.write(json);
writer.close();
System.out.println("JSON data written to " + path + " file.");
} catch (IOException e) {
e.printStackTrace();
}
}

/**

Saves a ScheduleList object to a CSV file.

@param schedules the ScheduleList object to be saved

@param path the path to the CSV file to be written
*/
public static void saveFileCsv(ScheduleList schedules, String path) {
Writer csvWriter;
try {
csvWriter = new FileWriter(path);
String lista = null;

// Get the attribute names of the Schedule class
String[] headers = { "curso", "unidadeCurricular", "turno", "turma", "inscritosNoTurno", "horarioInicioAula",
        "horarioFimAula", "diaSemana", "dataAula", "salaAtribuida", "lotacaoSala" };

// Create a CSVPrinter using Apache Commons CSV with ";" as the delimiter and the attribute names as headers
CSVPrinter csvPrinter = new CSVPrinter(csvWriter,
        CSVFormat.DEFAULT.withHeader(headers).withDelimiter(';'));

// Write the Schedule objects to the CSV file
for (Schedule schedule : schedules.getSchedules()) {
    if (lista == null || lista.contains(schedule.getUnidadeCurricular())) {
        csvPrinter.printRecord(schedule.getCurso(), schedule.getUnidadeCurricular(), schedule.getTurno(),
                schedule.getTurma(), schedule.getInscritosNoTurno(), schedule.getHorarioInicioAula(),
                schedule.getHorarioFimAula(), schedule.getDiaSemana(), schedule.getDataAula(),
                schedule.getSalaAtribuida(), schedule.getLotacaoSala());
    }
}
// Close the CSVPrinter and Writer
csvPrinter.close();
csvWriter.close();
} catch (IOException e) {
e.printStackTrace();
}
}
}