package main.java.tasks;

public class Subtask extends Task {
    private final int epicId = 0;

    public Subtask(String title, String description, Status status, int epicId) {
        super(title, description, status);
    }

    public Subtask(int id, String title, String status, String description, Status subTaskStatus) {
        super(id, description, subTaskStatus, description);
    }


    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}