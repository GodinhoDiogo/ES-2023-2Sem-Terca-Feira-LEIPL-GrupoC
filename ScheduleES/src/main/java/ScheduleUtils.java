import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import modules.Schedule;

public class ScheduleUtils {
    public static List<Schedule> filterByDay(Date date, List<Schedule> scheduleList) {
        List<Schedule> dailySchedule = new ArrayList<Schedule>();
        for (Schedule schedule : scheduleList) {
            if (isSameDay(schedule.getDataAula(), date)) {
                dailySchedule.add(schedule);
            }
        }
        return dailySchedule;
    }

    public static List<Schedule> filterByWeek(Date date, List<Schedule> scheduleList) {
        List<Schedule> weeklySchedule = new ArrayList<Schedule>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, -(startDay-1));
        for (int i = 0; i < 7; i++) {
            Date day = calendar.getTime();
            for (Schedule schedule : scheduleList) {
                if (isSameDay(schedule.getDataAula(), day)) {
                    weeklySchedule.add(schedule);
                }
            }
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        return weeklySchedule;
    }

    public static List<Schedule> filterByMonth(Date date, List<Schedule> scheduleList) {
        List<Schedule> monthlySchedule = new ArrayList<Schedule>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        for (Schedule schedule : scheduleList) {
            if (isSameMonth(schedule.getDataAula(), month)) {
                monthlySchedule.add(schedule);
            }
        }
        return monthlySchedule;
    }

    private static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = new GregorianCalendar();
        Calendar calendar2 = new GregorianCalendar();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    private static boolean isSameMonth(Date date, int month) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) == month;
    }
}
