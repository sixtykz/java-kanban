package main.java.tasks;

public class Subtask extends Task {

    private TaskType taskType;

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Subtask(int id, String title, String description, Status subTaskStatus) {
        super(id, description, subTaskStatus, description);
    }


    public int getEpicId() {
        int epicId = 0;
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