package data;


import util.Subject;

public class ScheduleSubject extends Subject<Schedule> {

    public Schedule getSchedule() {
        return super.getState();
    }

    public void setSchedule(Schedule schedule) {
        super.setState(schedule);
    }
}