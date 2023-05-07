package converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import modules.Schedule;
import modules.ScheduleList;

public class JsonToCsvConverterTest {

    private File jsonFile;
    private String csvFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        InputStream csvResource = getClass().getClassLoader().getResourceAsStream("sample.csv");
        if (csvResource != null) {
            File tempCsvFile = File.createTempFile("tempCsvFile", ".csv");
            tempCsvFile.deleteOnExit();
            try (OutputStream outputStream = new FileOutputStream(tempCsvFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = csvResource.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            }
            csvFilePath = tempCsvFile.getPath();
        }
        
        InputStream jsonResource = getClass().getClassLoader().getResourceAsStream("sample.json");
        if (jsonResource != null) {
            jsonFile = File.createTempFile("tempJsonFile", ".json");
            jsonFile.deleteOnExit();
            try (OutputStream outputStream = new FileOutputStream(jsonFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = jsonResource.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            }
        }
    }


    @Test
    public void testJsonToCsvConverter() throws IOException {
        ScheduleList scheduleList = JsonToCsvConverter.jsonToCsvConverted(jsonFile, csvFilePath, null);
        assertNotNull(scheduleList);
        assertEquals(3, scheduleList.getSchedules().size());

        List<String> csvLines = Files.readAllLines(Paths.get(csvFilePath));
        assertEquals(4, csvLines.size()); // 1 header line and 3 data lines
    }

    @Test
    public void testScheduleList() throws IOException {
        ScheduleList scheduleList = JsonToCsvConverter.scheduleList(jsonFile, null);
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