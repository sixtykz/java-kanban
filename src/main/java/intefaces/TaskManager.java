package main.java.intefaces;

import main.java.tasks.Status;
import main.java.tasks.Epic;
import main.java.tasks.Subtask;
import main.java.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {


    int createTask(Task task);

    int createEpic(Epic epic);

    int createSubTask(Subtask subTask);

    void deleteTaskById(int Id);

    void deleteEpicById(int Id);

    void deleteSubTaskById(int Id);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubTask();

    Task getTaskById(int Id);

    Epic getEpicById(int Id);

    Subtask getSubTaskById(int Id);

    List<Task> getAllTask();

    List<Epic> getAllEpic();

    List<Subtask> getAllSubTask();

    List<Subtask> getAllSubTaskByEpicId(int Id);

    void updateTask(Task task);

    void updateEpic(Epic epic);


    void updateSubtask(Subtask subTask);

    void printTasks();

    void printEpics();

    void printSubTask();

    List<Task> getHistory();
}