package main.java.service;

import main.java.intefaces.HistoryManager;
import main.java.tasks.Task;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> historyTasksList = new ArrayList<>();

    private static final int MAX_HISTORY_SIZE = 9;


    public void add(Task task) {

            if (historyTasksList.size() >= MAX_HISTORY_SIZE) {
                historyTasksList.remove(0);
            }
            historyTasksList.add(task);
        }


    public List<Task> getHistory() {
        return List.copyOf(historyTasksList);
    }

}