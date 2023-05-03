

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import modules.Schedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ScheduleJSONFileReader {

    public static List<Schedule> readFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }
        reader.close();

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Schedule>>(){}.getType();
        List<Schedule> scheduleList = gson.fromJson(jsonString.toString(), listType);

        return scheduleList;
    }

}
