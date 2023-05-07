/**

This package contains classes for converting CSV files to JSON and JSON files to CSV.
*/
package converters;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import com.google.gson.Gson;
import modules.ScheduleList;

/**

The JsonToCsvConverter class provides methods for converting a JSON file to a ScheduleList object.

The class contains a static method that takes an InputStream and a List of course names as arguments.

The method reads the JSON input stream, uses Gson library to convert the JSON data to a ScheduleList object,

and returns the converted object.

@author [Your Name]

@version 1.0

@since [Date of creation]
*/
public class JsonToCsvConverterTest2 {

/**

Converts a JSON input stream to a ScheduleList object filtered by the given list of course names.
@param jsonInputStream An InputStream containing JSON data.
@param lista A List of course names to filter the ScheduleList object.
@return A ScheduleList object containing schedules filtered by the given course names.
@throws IOException If an I/O error occurs while reading the input stream.
*/
public static ScheduleList jsonToCsvConverted(InputStream jsonInputStream,List<String> lista) throws IOException {
ScheduleList schedules = scheduleList(jsonInputStream, lista);
return schedules;
}
/**

Converts a JSON input stream to a ScheduleList object.
@param jsonInputStream An InputStream containing JSON data.
@param lista A List of course names to filter the ScheduleList object.
@return A ScheduleList object containing all schedules from the JSON data.
@throws IOException If an I/O error occurs while reading the input stream.
*/
public static ScheduleList scheduleList(InputStream jsonInputStream, List<String> lista) throws IOException {
try (BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInputStream))) {
Gson gson = new Gson();
ScheduleList schedules = gson.fromJson(reader, ScheduleList.class);
return schedules;
}
}
}




