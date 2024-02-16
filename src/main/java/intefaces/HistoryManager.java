package main.java.intefaces;

import main.java.tasks.Task;
import java.util.List;

public interface HistoryManager {

    void add(Task task);

    // Удаление просмотра из истории
    void remove(int id);

    List<Task> getHistory();
}