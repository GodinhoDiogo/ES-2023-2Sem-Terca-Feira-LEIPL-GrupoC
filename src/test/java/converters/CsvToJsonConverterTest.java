package converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import modules.Schedule;
import modules.ScheduleList;

public class CsvToJsonConverterTest {

    private InputStream csvInputStream;
    private InputStream jsonInputStream;

    @BeforeEach
    public void setUp() {
        csvInputStream = getClass().getClassLoader().getResourceAsStream("sample.csv");
        jsonInputStream = getClass().getClassLoader().getResourceAsStream("sample.json");
    }


    @Test
    public void testCsvToJsonConverter() throws IOException {
        ScheduleList scheduleList = CsvToJsonConverter.CsvToJsonConverted(csvInputStream, null);
        assertNotNull(scheduleList);
        assertEquals(3, scheduleList.getSchedules().size());
    }

    @Test
    public void testScheduleList() throws IOException {
        ScheduleList scheduleList = CsvToJsonConverter.scheduleList(csvInputStream, null);
        assertNotNull(scheduleList);
        assertEquals(3, scheduleList.getSchedules().size());

        Schedule schedule = scheduleList.getSchedules().get(0);
        assertEquals("ME", schedule.getCurso());
        assertEquals("Teoria dos Jogos e dos Contratos", schedule.getUnidadeCurricular());
        assertEquals("01789TP01", schedule.getTurno());
        assertEquals("MEA1", schedule.getTurma());
        assertEquals("ME", schedule.getCurso());
        assertEquals("30", schedule.getInscritosNoTurno());
        assertEquals("13:00:00", schedule.getHorarioInicioAula());
        assertEquals("14:30:00", schedule.getHorarioFimAula());
        assertEquals("Sex", schedule.getDiaSemana());
        assertEquals("02/12/2022", schedule.getDataAula());
        assertEquals("AA2.25", schedule.getSalaAtribuida());
        assertEquals("34", schedule.getLotacaoSala());
        // Add more assertions to validate the other fields
    }
}
