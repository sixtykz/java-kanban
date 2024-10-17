package main.java.tasks;

import java.time.Instant;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String title, String description, Status subTaskStatus, Instant startTime, long duration) {
        super(id, title, description, subTaskStatus, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(int id, String title, String description, Status status) {
        super(id, title, description, status);
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + getEpicId() +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}