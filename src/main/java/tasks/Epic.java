package main.java.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksList = new ArrayList<>();

    public Epic(String title, String description, Status status) {
        super(title, description, status);

    }

    public Epic(String description, String name, Status status, int id) {
        super(id, description, name, status);
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