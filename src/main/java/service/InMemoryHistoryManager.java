package main.java.service;

import main.java.intefaces.HistoryManager;
import main.java.tasks.Task;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> historyTasksList = new ArrayList<>();

    static final int cons = 9;


    public void add(Task task) {

            if (historyTasksList.size() >= cons) {
                historyTasksList.remove(0);
            }
            historyTasksList.add(task);
        }


    public List<Task> getHistory() {
        return List.copyOf(historyTasksList);
    }

}