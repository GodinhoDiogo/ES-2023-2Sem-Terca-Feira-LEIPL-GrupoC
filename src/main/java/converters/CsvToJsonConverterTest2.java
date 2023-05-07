/**

The converters package provides classes to convert data from one format to another.
*/
package converters;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import modules.Schedule;
import modules.ScheduleList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**

The CsvToJsonConverter class converts data from a CSV file to a ScheduleList object containing Schedule objects.
*/
public class CsvToJsonConverterTest2 {

/**

Converts a CSV file to a ScheduleList object containing Schedule objects.

@param file the CSV file to be converted

@param lista a list of unit names to filter the data by (optional)

@return the ScheduleList object containing Schedule objects

@throws IOException if an I/O error occurs while reading the file
*/
public static ScheduleList CsvToJsonConverted(File file, List<String> lista) throws IOException {

// Create a ScheduleList object from the CSV file and return it
ScheduleList schedules = scheduleList(file, lista);
return schedules;
}



/**

Creates a ScheduleList object from a CSV file.

@param file the CSV file to be read

@param lista a list of unit names to filter the data by (optional)

@return the ScheduleList object containing Schedule objects

@throws IOException if an I/O error occurs while reading the file
*/
public static ScheduleList scheduleList(File file, List<String> lista) throws IOException {

// Create a FileReader object to read the CSV file
Reader reader = new FileReader(file);

// Create a CSVParser using Apache Commons CSV with ";" as the delimiter
CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'));

// Create a ScheduleList object to hold Schedule objects
ScheduleList schedules = new ScheduleList();

// Iterate through the CSV records
boolean firstRow = true;
for (CSVRecord record : csvParser) {
if (firstRow) {
firstRow = false;
continue; // Skip the first row
}
// Extract the values from each column
String curso = record.get(0);
String unidadeCurricular = record.get(1);
String turno = record.get(2);
String turma = record.get(3);
String inscritosNoTurno = record.get(4);
String diaSemana = record.get(5);
String horarioInicioAula = record.get(6);
String horarioFimAula = record.get(7);
String dataAula = record.get(8);
String salaAtribuida = record.get(9);
String lotacaoSala = record.get(10);

//Create a Schedule object and add it to the ScheduleList
if (lista == null || lista.contains(unidadeCurricular)) {
  Schedule schedule = new Schedule(curso, unidadeCurricular, turno, turma, inscritosNoTurno, diaSemana,
          horarioInicioAula, horarioFimAula, dataAula, salaAtribuida, lotacaoSala);
  schedules.add(schedule);
}
}
//Close the CSVParser and Reader
csvParser.close();
reader.close();

return schedules;
}

}