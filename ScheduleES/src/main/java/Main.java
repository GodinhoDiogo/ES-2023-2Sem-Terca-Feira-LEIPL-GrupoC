import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Throwable  {
        try {
			JSONtoCSV.convertJsonToCsv("/Users/tomascarvalho/Desktop/GitHub/ProjetoES/ScheduleES/src/main/java/input.json", "output.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}