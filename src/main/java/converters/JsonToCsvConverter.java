package converters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import com.google.gson.Gson;
import modules.ScheduleList;

public class JsonToCsvConverter {
    public static ScheduleList jsonToCsvConverted(InputStream jsonInputStream,List<String> lista) throws IOException {
        ScheduleList schedules = scheduleList(jsonInputStream, lista);
        return schedules;
    }

    public static ScheduleList scheduleList(InputStream jsonInputStream, List<String> lista) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInputStream))) {
            Gson gson = new Gson();
            ScheduleList schedules = gson.fromJson(reader, ScheduleList.class);
            return schedules;
        }
    }
}