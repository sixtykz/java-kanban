package main.java.tasks;

public class Subtask extends Task {

    private int epicId;

    public Subtask(int id, String title, String description, Status subTaskStatus) {
        super(id, title, description, subTaskStatus);

        this.epicId = epicId;
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