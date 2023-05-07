package modules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ScheduleListTest {

    @Test
    public void testFromWebcalString() throws IOException {
        
        String webcalData = "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "PRODID:-//Test//Test Calendar//EN\n" +
                "BEGIN:VEVENT\n" +
                "UID:20210507T082000Z-1\n" +
                "DTSTAMP:20210507T082000Z\n" +
                "DTSTART:20210507T082000Z\n" +
                "DTEND:20210507T092000Z\n" +
                "SUMMARY:Unidade Curricular 1\n" +
                "LOCATION:Sala 1\n" +
                "DESCRIPTION:Shift: T1\\nOther participants /Outros participantes: Rui Marinheiro\n" +
                "END:VEVENT\n" +
                "BEGIN:VEVENT\n" +
                "UID:20210507T102000Z-2\n" +
                "DTSTAMP:20210507T102000Z\n" +
                "DTSTART:20210507T102000Z\n" +
                "DTEND:20210507T112000Z\n" +
                "SUMMARY:Unidade Curricular 2\n" +
                "LOCATION:Sala 2\n" +
                "DESCRIPTION:Shift: T2\\nOther participants /Outros participantes: Adriano Queiroz\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR";
        ScheduleList scheduleList = ScheduleList.fromWebcalString(webcalData);

        assertNotNull(scheduleList);
        assertEquals(2, scheduleList.getSchedules().size());

        Schedule schedule = scheduleList.getSchedules().get(0);
        assertEquals("Unidade Curricular 1", schedule.getUnidadeCurricular());
        assertEquals("Sala 1", schedule.getSalaAtribuida());
        assertEquals("T1", schedule.getTurno());
        // Add more assertions to validate the other fields
    }
}
