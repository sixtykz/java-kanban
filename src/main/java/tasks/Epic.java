package main.java.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksList;

    public Epic(String title, String description, Status status, ArrayList<Integer> subtasksList) {
        super(title, description, status);
        this.subtasksList = subtasksList;
    }

    public ArrayList<Integer> getSubtasksList() {
        return subtasksList;
    }

    public void cleanSubtaskIds() {
        subtasksList.clear();
    }
    public void removeSubtask(int id) {
        subtasksList.remove((Integer) id);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", subtasksList=" + subtasksList +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\''
                + '}';
    }

}