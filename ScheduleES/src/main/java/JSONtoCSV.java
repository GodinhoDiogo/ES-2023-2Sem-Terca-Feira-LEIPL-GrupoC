import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONtoCSV{
	
	
	public static void convertJsonToCsv( String s, String str ) throws Throwable {
		
		JSONParser jsonParser = new JSONParser();

	try (FileReader reader = new FileReader(s))
    {
        //Read JSON file
        Object obj = jsonParser.parse(reader);
        parseObject((JSONObject) obj);

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
	
	}
	
	private static void parseObject(JSONObject obj) {
		
		JSONArray items= (JSONArray) obj.get("items");
	
		System.out.println(items.size());
		
		
	}
	
}
