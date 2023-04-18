package modules;

import java.util.ArrayList;
import java.util.List;

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
}
