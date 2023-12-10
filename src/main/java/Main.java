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

        System.out.println("*** Task ***");
        System.out.println("--- Create task ---");
        manager.createTask(new Task("Описание-1", "Task-1", Status.NEW));
        manager.createTask(new Task("Описание-2", "Task-2", Status.NEW));
        manager.printTasks();
        System.out.println("--- Get all tasks ---");
        List<Task> taskList = manager.getAllTask();
        System.out.println(taskList);
        System.out.println("--- Get task by id ---");
        Task task = manager.getTaskById(1);
        System.out.println(task);
        System.out.println("--- Update task ---");
        task.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task);
        System.out.println(task);
        System.out.println();

        System.out.println("*** Epic ***");
        System.out.println("--- Create epic ---");
        manager.createEpic(new Epic("Описание-1", "Epic-1", Status.NEW));
        manager.createEpic(new Epic("Описание-2", "Epic-2", Status.NEW));
        manager.printEpics();
        System.out.println("--- Get all epics ---");
        List<Epic> epics = manager.getAllEpic();
        System.out.println(epics);
        System.out.println("--- Get epic by id ---");
        Epic epic = manager.getEpicById(3);
        System.out.println(epic);
        System.out.println("--- Update epic ---");
        epic.setStatus(Status.IN_PROGRESS);
        manager.updateEpic(epic);
        Epic epic3 = manager.getEpicById(3);
        System.out.println(epic3);
        System.out.println();

        System.out.println("*** Subtask ***");
        System.out.println("--- Create subtask ---");
        manager.createSubTask(new Subtask("Описание-1", "Subtask-1", Status.NEW, 3));
        manager.createSubTask(new Subtask("Описание-2", "Subtask-2", Status.NEW, 3));
        manager.createSubTask(new Subtask("Описание-3", "Subtask-3", Status.NEW, 4));
        manager.createSubTask(new Subtask("Описание-4", "Subtask-4", Status.NEW, 4));
        manager.printSubTask();
        System.out.println("--- Get all subtasks by epic id ---");
        List<Subtask> subtasksByEpicId = manager.getAllSubTaskByEpicId(3);
        System.out.println(subtasksByEpicId);
        System.out.println("--- Get all subtasks ---");
        List<Subtask> subtasks = manager.getAllSubTask();
        System.out.println(subtasks);
        System.out.println("--- Get subtask by id ---");
        Subtask subtask = manager.getSubTaskById(5);
        System.out.println(subtask);
        System.out.println("--- Update subtask ---");
        subtask.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask);
        System.out.println(subtask);
        System.out.println();

        System.out.println("*** Delete ***");
        System.out.println("--- Delete task by id ---");
        manager.deleteTaskById(1);
        System.out.println(taskList);
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

    }
} 
