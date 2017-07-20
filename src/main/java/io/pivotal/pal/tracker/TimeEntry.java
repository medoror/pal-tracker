package io.pivotal.pal.tracker;

import java.sql.Time;
import java.time.LocalDate;

public class TimeEntry {

    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    public TimeEntry(){}

    public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.date = date;
        this.hours = hours;
        this.userId = userId;

    }

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
        this.projectId = projectId;
        this.date = date;
        this.userId = userId;
        this.hours = hours;

    }

    public TimeEntry( TimeEntry that){
        this.id = that.id;
        this.userId = that.userId;
        this.date = that.date;
        this.hours = that.hours;
        this.projectId = that.projectId;
    }

    public Long getId() {
        return this.id;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }
}
