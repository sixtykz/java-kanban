package main.java.service;

import main.java.intefaces.HistoryManager;
import main.java.intefaces.TaskManager;

import java.io.File;

public class Managers {

    public static  HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public static FileBackedTasksManager getDefaultManager(File File){
        return new FileBackedTasksManager(File);
    }

}


