package modules;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	public ScheduleList(List<Schedule> schedules) {
		this.schedules = schedules;
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
	        String date = new SimpleDateFormat("dd/MM/yyyy").format(event.getDateStart().getValue());
	        String dayOfTheWeek = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).getDayOfWeek().toString().substring(0, 3);
	        String begin = new SimpleDateFormat("HH:mm:ss").format(event.getDateStart().getValue());
	        String end = new SimpleDateFormat("HH:mm:ss").format(event.getDateEnd().getValue());
	        String location = event.getLocation().getValue();
	        String uc = event.getSummary().getValue().split("-")[0];
	        //Done: begin, end, location, inscritos no turno, uc
	        String scheduleDate = String.format("%s %s", dayOfTheWeek, date);
	        String formattedBeginTime = String.format("%s:00", begin);
	        String formattedEndTime = String.format("%s:00", end);
	        Schedule schedule = new Schedule("", uc, turno, "", getInscritosFromDescription(description), scheduleDate, formattedBeginTime, formattedEndTime, date, location, null);
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

	
}
