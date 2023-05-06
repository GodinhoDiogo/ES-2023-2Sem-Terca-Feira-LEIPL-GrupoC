package modules;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

public class ScheduleList {
	private List<Schedule> schedules;

	public ScheduleList() {
		schedules =  new ArrayList<Schedule>();
	}
	public void add(Schedule s) {
		schedules.add(s);
	}
	public List<Schedule> getSchedules() {
		return schedules;
	}
public static ScheduleList fromWebcalString(String webcalData) {
		
	    ScheduleList schedules = new ScheduleList();
	    ICalendar ical = Biweekly.parse(webcalData).first();
	    System.out.println("this is gonna run");
	    for (VEvent event : ical.getEvents()) {
	    	String docente = "";
	    	String description = event.getDescription().getValue();
	    	String summary = event.getSummary().getValue();
	    	if(!summary.contains("Alunos") || !summary.contains("Exame")) {
	    		String docenteTag = "Docente: "; // The tag that appears before the docent value
	    		int docenteTagIndex = description.indexOf(docenteTag); // Find the index of the docente tag
	    		int docenteStartIndex = docenteTagIndex + docenteTag.length(); // Calculate the starting index of the docente value
	    		int docenteEndIndex = description.indexOf('\n', docenteStartIndex); // Find the index of the end of the docente value
	    		docente = description.substring(docenteStartIndex, docenteEndIndex).trim(); // Extract the docente value and remove any leading or trailing whitespace
	    	}  	
	    	System.out.println("--------------------------");
	    	//System.out.println(description);
	    	Pattern pattern = Pattern.compile("Shift:\\s+(\\w+)");
	    	String turno = "";
	         // Use a Matcher to find the first occurrence of the pattern in the text
	         Matcher matcher = pattern.matcher(description);
	         if (matcher.find()) {
	             turno = matcher.group(1);
	             
	         }
	        String date = new SimpleDateFormat("yyyy-MM-dd").format(event.getDateStart().getValue());
	        String dayOfTheWeek = LocalDate.parse(date).getDayOfWeek().toString();
	        String begin = new SimpleDateFormat("HH:mm").format(event.getDateStart().getValue());
	        String end = new SimpleDateFormat("HH:mm").format(event.getDateEnd().getValue());
	        System.out.println(date);
	        String location = event.getLocation().getValue();
	        String uc = event.getSummary().getValue().split("-")[0];
//Done: begin, end, location, inscritos no turno, uc
	       // Schedule schedule = new Schedule(null, unidadeCurricular, null, null, null, null, begin, end, dataAula, location, null);
	        Schedule schedule = new Schedule("", uc, turno, "", getInscritosFromDescription(description), dayOfTheWeek, begin, end, date, location, null);
	        schedules.add(schedule);
	    }

	    return schedules;
	}
	private static String getInscritosFromDescription(String description) {
		String outrosParticipantesTag = "Other participants /Outros participantes: ";
		if(!description.contains(outrosParticipantesTag)) return "";
		
		int outrosParticipantesIndex = description.indexOf(outrosParticipantesTag) + outrosParticipantesTag.length();
		
		String outrosParticipantes = description.substring(outrosParticipantesIndex).trim();

		StringBuilder names = new StringBuilder(); // A StringBuilder to store the names of the responsible participant and other participants

		

		// Split the list of other participants into an array of names and append each name to the StringBuilder
		String[] outrosParticipantesArray = outrosParticipantes.split("\\s+");
		for (int i = 0; i < outrosParticipantesArray.length; i++) {
		    String name = outrosParticipantesArray[i].trim();
		    if (!name.isEmpty()) { // Check if the name is not empty
		        names.append(", ");
		        names.append(name);
		    }
		}
		return names.toString();
	}

	private static String extractValue(String eventData, String regex) {
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(eventData);

	    if (matcher.find()) {
	        return matcher.group(1).trim();
	    }

	    return "";
	}
}
