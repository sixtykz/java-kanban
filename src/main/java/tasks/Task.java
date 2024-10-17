package main.java.tasks;

import java.time.Instant;

public class Task {
    private int id;
    private String title;
    private String description;
    private Status status;
    public TaskType taskType;
    private transient Instant startTime;
    private long duration;

    public Task(int id, String title, String description, Status status, Instant startTime, long duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;

    }

    public Task(int id, String testTask, String description, Status status) {
    }

    public Task(String description, String title, Status status, Instant now, int i) {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Instant getEndTime() {
        long SECONDS_IN_MINUTE = 60L;
        return startTime.plusSeconds(duration * SECONDS_IN_MINUTE);
    }


    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }

    public boolean toCSV() {
        return true;
    }
}