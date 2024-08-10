package main.java.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksList = new ArrayList<>();

    public Epic(int id, String title, Status status, String description) {
        super(id, description, status, description);
    }

    public Epic(int id, String title, String s, Status status) {
        super(id, title, s, status);
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