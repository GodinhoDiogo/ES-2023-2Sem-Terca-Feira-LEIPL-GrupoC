package converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import modules.Schedule;
import modules.ScheduleList;

public class CsvToJsonConverterTest {

    private File csvFile;
    private String jsonFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        InputStream csvResource = getClass().getClassLoader().getResourceAsStream("sample.csv");
        if (csvResource != null) {
            csvFile = File.createTempFile("tempCsvFile", ".csv");
            csvFile.deleteOnExit();
            try (OutputStream outputStream = new FileOutputStream(csvFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = csvResource.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            }
        }
        
        InputStream jsonResource = getClass().getClassLoader().getResourceAsStream("sample.json");
        if (jsonResource != null) {
            File tempJsonFile = File.createTempFile("tempJsonFile", ".json");
            tempJsonFile.deleteOnExit();
            try (OutputStream outputStream = new FileOutputStream(tempJsonFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = jsonResource.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            }
            jsonFilePath = tempJsonFile.getPath();
        }
    }

    @Test
    public void testCsvToJsonConverter() throws IOException {
        ScheduleList scheduleList = CsvToJsonConverter.CsvToJsonConverted(csvFile, null);
        assertNotNull(scheduleList);
        assertEquals(3, scheduleList.getSchedules().size());

        String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        Gson gson = new Gson();
        ScheduleList convertedList = gson.fromJson(json, ScheduleList.class);

        assertEquals(scheduleList.getSchedules().size(), convertedList.getSchedules().size());
    }

    @Test
    public void testScheduleList() throws IOException {
        ScheduleList scheduleList = CsvToJsonConverter.scheduleList(csvFile, null);
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
