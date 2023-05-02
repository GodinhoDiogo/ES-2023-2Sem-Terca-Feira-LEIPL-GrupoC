package converters;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.google.gson.Gson;

import modules.Schedule;
import modules.ScheduleList;

public class CsvToJsonConverter {
	public static void CsvToJsonConverted(File file, String path) throws IOException {
		// Specify the path to your CSV file
        //String filePath = "C:\\Users\\nanor\\Downloads\\horario_exemplo.csv";

        // Create a reader for the CSV file
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
            String horarioFimAula = record.get(5);
            String diaSemana = record.get(6);
            String horarioInicioAula = record.get(7);
            String dataAula = record.get(8);
            String salaAtribuida = record.get(9);
            String lotacaoSala = record.get(10);

            // Create a Schedule object and add it to the ScheduleList
            Schedule schedule = new Schedule(curso, unidadeCurricular, turno, turma, inscritosNoTurno, diaSemana,
                    horarioInicioAula, horarioFimAula, dataAula, salaAtribuida, lotacaoSala);
            schedules.add(schedule);
        }

        // Convert ScheduleList object to JSON
        Gson gson = new Gson();
        String json = gson.toJson(schedules);

        // Write JSON to a file
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(json);
            writer.close();
            System.out.println("JSON data written to schedules.json file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the CSVParser and Reader
        csvParser.close();
        reader.close();
//        return file;
	}
//	public static void main(String[] args) throws IOException {
//		CsvToJsonConverted("C:\\Users\\nanor\\Downloads\\horario_exemplo.csv","C:\\Users\\nanor\\OneDrive\\Ambiente de Trabalho");
//	}
}
