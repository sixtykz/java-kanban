package main.java.service;

import main.java.intefaces.HistoryManager;
import main.java.intefaces.TaskManager;

public class Managers {

    public static  HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
