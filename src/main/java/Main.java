package main.java;

import main.java.service.InMemoryTaskManager;
import main.java.tasks.Epic;
import main.java.tasks.Status;
import main.java.tasks.Subtask;
import main.java.tasks.Task;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        System.out.println("*** Test History ***");
        System.out.println("--- Create ---");
        manager.createTask(new Task("Описание-1", "Task-1", Status.NEW)); // id 1
        manager.createTask(new Task("Описание-2", "Task-2", Status.NEW)); // id 2
        manager.createEpic(new Epic("Описание-1", "Epic-1", Status.NEW)); // id 3
        manager.createEpic(new Epic("Описание-1", "Epic-2", Status.NEW)); // id 4
        manager.createSubTask(new Subtask("Описание-1", "Subtask-1", Status.NEW, 3)); // id 5
        manager.createSubTask(new Subtask("Описание-2", "Subtask-2", Status.NEW, 3)); // id 6
        manager.createSubTask(new Subtask("Описание-3", "Subtask-3", Status.NEW, 3)); // id 7

        System.out.println("--- Get By Id ---");
        manager.getTaskById(1);
        manager.getEpicById(3);
        manager.getEpicById(3);
        manager.getEpicById(3);
        manager.getTaskById(1);
        manager.getEpicById(4);
        manager.getSubTaskById(5);
        manager.getSubTaskById(5);
        manager.getSubTaskById(6);

        System.out.println("*** Delete ***");
        System.out.println("--- Delete task by id ---");
        manager.deleteTaskById(1);
        System.out.println("--- Delete all tasks ---");
        manager.deleteAllTasks();
        manager.printTasks();
        System.out.println("--- Delete subtask by id ---");
        manager.deleteSubTaskById(5);
        manager.printSubTask();
        System.out.println("--- Delete all subtasks ---");
        manager.deleteAllSubTask();
        manager.printSubTask();
        System.out.println("--- Delete epic by id ---");
        manager.deleteEpicById(4);
        manager.printEpics();
        System.out.println("--- Delete all epics ---");
        manager.deleteAllEpics();
        manager.printEpics();


        System.out.println("--- Get History ---");
        List<Task> history = manager.getHistory();
        System.out.println(history);

        System.out.println("--- Remove from history ---");
        manager.remove(1);
        manager.deleteEpicById(3);

        List<Task> historyAfterRemove = manager.getHistory();
        System.out.println(historyAfterRemove);
    }
} 

