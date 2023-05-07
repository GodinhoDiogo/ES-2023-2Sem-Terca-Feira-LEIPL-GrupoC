package converters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import modules.Schedule;
import modules.ScheduleList;

public class CsvToJsonConverter {
    public static ScheduleList CsvToJsonConverted(InputStream csvInputStream, List<String> lista) throws IOException {
        ScheduleList schedules = scheduleList(csvInputStream, lista);
        return schedules;
    }

    public static ScheduleList scheduleList(InputStream csvInputStream, List<String> lista) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvInputStream));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'))) {

            ScheduleList schedules = new ScheduleList();
            boolean firstRow = true;

            for (CSVRecord record : csvParser) {
                if (firstRow) {
                    firstRow = false;
                    continue; // Skip the first row
                }

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

                if (lista == null || lista.contains(unidadeCurricular)) {
                    Schedule schedule = new Schedule(curso, unidadeCurricular, turno, turma, inscritosNoTurno, diaSemana,
                            horarioInicioAula, horarioFimAula, dataAula, salaAtribuida, lotacaoSala);
                    schedules.add(schedule);
                }
            }

            return schedules;
        }
    }
}
