package main.java.service;

import main.java.intefaces.HistoryManager;

public class Managers {

    public static  HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}