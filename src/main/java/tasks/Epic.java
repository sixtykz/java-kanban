package main.java.tasks;

import java.util.ArrayList;
import java.time.Instant;


public class Epic extends Task {
    private final ArrayList<Integer> subtasksList = new ArrayList<>();
    private transient Instant endTime;

    public Epic(int id, String title,  String description, Status epicStatus, Instant startTime, long duration) {
        super(id, title, description, epicStatus, startTime, duration);
        this.endTime = super.getEndTime();
    }

    public Epic(int id, String title, String description, Status status) {
        super(id, title, description, status);
    }


    public ArrayList<Integer> getSubtasksList() {
        return subtasksList;
    }

    public void setSubtasksList(int id) {
        subtasksList.add(id);
    }


    @Override
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", subtasksList=" + getSubtasksList() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\''
                + '}';
    }

}