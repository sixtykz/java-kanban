package main.java.service;

import main.java.intefaces.HistoryManager;

import java.io.File;

public class Managers {

    private static File file;

    public static  HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public static FileBackedTasksManager getDefaultManager() {
        return new FileBackedTasksManager(file);
    }

}


