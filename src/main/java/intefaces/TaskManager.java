package main.java.intefaces;

import main.java.tasks.Status;
import main.java.tasks.Epic;
import main.java.tasks.Subtask;
import main.java.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Subtask> getSubtasks();

    void taskClean();

    void epicClean();

    void subtaskClean();

    Task getTaskById(int taskId);

    Epic getEpicById(int taskId);

    Subtask getSubtaskById(int taskId);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int id);

    void changeStatusTask(int id, Status status);

    void changeStatusSubtask(int id, Status status);

    ArrayList<Integer> getSubtaskList(int epicId);
    
    void updateEpicStatus(int epicId);

    List<Task> historyList();

}