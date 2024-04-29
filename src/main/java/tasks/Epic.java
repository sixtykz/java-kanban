package main.java.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksList = new ArrayList<>();

    public Epic(String title, String description, Status status) {
        super(title, description, status);

    }

    public Epic(int id, String title, String status, String description) {
        super(id, description, status, description);
    }

    public ArrayList<Integer> getSubtasksList() {
        return subtasksList;
    }

    public void setSubtasksList(int id) {
        subtasksList.add(id);
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