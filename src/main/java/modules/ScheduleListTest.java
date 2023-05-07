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

public class ScheduleListTest {

	private List<Schedule> schedules;

	/**
	 * Constructs an empty ScheduleList object.
	 */
	public ScheduleListTest() {
		schedules = new ArrayList<Schedule>();
	}

	/**
	 * Constructs a ScheduleList object with the specified list of schedules.
	 *
	 * @param schedules the list of schedules to be added to the ScheduleList object
	 */
	public ScheduleListTest(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	/**
	 * Adds a schedule to the ScheduleList object.
	 *
	 * @param s the schedule to be added to the ScheduleList object
	 */
	public void add(Schedule s) {
		schedules.add(s);
	}

	/**
	 * Returns the list of schedules in the ScheduleList object.
	 *
	 * @return the list of schedules in the ScheduleList object
	 */
	public List<Schedule> getSchedules() {
		return schedules;
	}

	/**
	 * 
	 * Converts a webcal string to a ScheduleList object.
	 * 
	 * @param webcalData the webcal string to convert.
	 * 
	 * @return a ScheduleList object containing the parsed schedules.
	 */
	public static ScheduleList fromWebcalString(String webcalData) {
		ScheduleList schedules = new ScheduleList();

		// Parse the webcalData string using the Biweekly library.
		ICalendar ical = Biweekly.parse(webcalData).first();

		// Iterate over each VEvent in the ICalendar object.
		for (VEvent event : ical.getEvents()) {
			String docente = "";
			String description = event.getDescription().getValue();
			String summary = event.getSummary().getValue();
			// If the event summary contains "Alunos" or "Exame", skip it.
			if (!summary.contains("Alunos") || !summary.contains("Exame")) {
				String docenteTag = "Docente: "; // The tag that appears before the docent value
				int docenteTagIndex = description.indexOf(docenteTag); // Find the index of the docente tag
				int docenteStartIndex = docenteTagIndex + docenteTag.length(); // Calculate the starting index of the
																				// docente value
				int docenteEndIndex = description.indexOf('\n', docenteStartIndex); // Find the index of the end of the
																					// docente value
				docente = description.substring(docenteStartIndex, docenteEndIndex).trim(); // Extract the docente value
																							// and remove any leading or
																							// trailing whitespace
			}

			// Find the "Shift" pattern in the event description using a regex pattern and
			// matcher.
			Pattern pattern = Pattern.compile("Shift:\\s+(\\w+)");
			String turno = "";
			Matcher matcher = pattern.matcher(description);
			if (matcher.find()) {
				turno = matcher.group(1);
			}

			// Get the date, day of the week, begin time, end time, location, and course
			// from the VEvent object.
			String date = new SimpleDateFormat("dd/MM/yyyy").format(event.getDateStart().getValue());
			String dayOfTheWeek = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).getDayOfWeek()
					.toString().substring(0, 3);
			String begin = new SimpleDateFormat("HH:mm:ss").format(event.getDateStart().getValue());
			String end = new SimpleDateFormat("HH:mm:ss").format(event.getDateEnd().getValue());
			String location = event.getLocation().getValue();
			String uc = event.getSummary().getValue().split("-")[0];

			// Get the number of registered students for the turno from the event
			// description.
			String inscritos = getInscritosFromDescription(description);

			// Format the schedule date, begin time, and end time, and create a new Schedule
			// object with the parsed data.
			String scheduleDate = String.format("%s %s", dayOfTheWeek, date);
			String formattedBeginTime = String.format("%s:00", begin);
			String formattedEndTime = String.format("%s:00", end);
			Schedule schedule = new Schedule("", uc, turno, "", inscritos, scheduleDate, formattedBeginTime, formattedEndTime, date, location, null);

			// Add the new Schedule object to the ScheduleList.
			schedules.add(schedule);
		}

		// Return the ScheduleList object containing the parsed schedules.
		return schedules;
	}

	/**

	Parses the description of an event to extract the names of other participants in the event.

	@param description the description of the event to parse

	@return a string containing the names of other participants, separated by commas, or an empty string if no other participants are found
	*/
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