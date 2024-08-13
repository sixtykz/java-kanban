package main.java.service;

import main.java.intefaces.HistoryManager;

import java.io.File;

public class Managers {

    public static  HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public static FileBackedTasksManager getDefaultManager(File file){
        return new FileBackedTasksManager(file);
    }

}


