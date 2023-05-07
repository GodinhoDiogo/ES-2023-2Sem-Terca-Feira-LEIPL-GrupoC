package converters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import com.google.gson.Gson;
import modules.ScheduleList;

public class JsonToCsvConverter {
    public static ScheduleList jsonToCsvConverted(File file, String path, List<String> lista) throws IOException {
        // Create a reader for the JSON file
        Reader jsonReader = new FileReader(file);

        // Parse the JSON file into a list of Schedule objects
        Gson gson = new Gson();
        ScheduleList schedules = gson.fromJson(jsonReader, ScheduleList.class);

        // Create a writer for the CSV file
        return schedules;
    }
    
    
    public static ScheduleList scheduleList(File file, List<String> lista) throws IOException {
    	 Reader jsonReader = new FileReader(file);

         // Parse the JSON file into a list of Schedule objects
         Gson gson = new Gson();
         ScheduleList schedules = gson.fromJson(jsonReader, ScheduleList.class);

         return schedules;
        	 
         
    }
    
}
