package main.java.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksList = new ArrayList<>();

    public Epic(int id, String title, Status status, String description) {
        super(id, description, status, Status.valueOf(description));
    }

    public Epic(int id, String title, Status s, Status status) {
        super(id, title, s, status);
    }

    public Epic(int description, String name, String status, Status id) {
        super(description, name, status, id);
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