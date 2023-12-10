package main.java.service;

import main.java.intefaces.HistoryManager;
import main.java.tasks.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> historyTasksList = new LinkedList<>();

    public void add(Task task) {
        if (historyTasksList.size() <= 9) {
            if (historyTasksList.size() == 9) {
                historyTasksList.remove(0);
            }
            historyTasksList.add(task);
        }
    }

    public List<Task> getHistory() {
        return historyTasksList;
    }

}