package main.java;

import main.java.service.FileBackedTasksManager;
import main.java.service.InMemoryTaskManager;
import main.java.tasks.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        InMemoryTaskManager manager = new InMemoryTaskManager() {

        };

        System.out.println("*** Test History ***");
        System.out.println("--- Create ---");
        manager.createTask(new Task("Описание-1", "Task-1", Status.NEW));
        manager.createTask(new Task("Описание-2", "Task-2", Status.NEW));
        manager.createEpic(new Epic("Описание-1", "Epic-1", Status.NEW));
        manager.createEpic(new Epic("Описание-1", "Epic-2", Status.NEW));
        manager.createSubTask(new Subtask("Описание-1", "Subtask-1", Status.NEW, 3));
        manager.createSubTask(new Subtask("Описание-2", "Subtask-2", Status.NEW, 3));
        manager.createSubTask(new Subtask("Описание-3", "Subtask-3", Status.NEW, 3));

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
        manager.deleteEpicById(3);

        List<Task> historyAfterRemove = manager.getHistory();
        System.out.println(historyAfterRemove);


        System.out.println("--- SaveAndLoadEmptyFile ---");

        File file = File.createTempFile("empty_file", ".csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        FileBackedTasksManager.loadFromFile(file);

        System.out.println("Save and load empty file has been successful");

        System.out.println("--- SaveAndLoadMultipleTasks ---");
        file = File.createTempFile("multiple_tasks", ".csv");
        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(file);

        Task task1 = new Task(Task.getId(), Task.getTitle(), "Task 1", "Description 1");
        Task task2 = new Task(Task.getId(), Task.getTitle(), "Task 2", "Description 2");

        Task epic1 = new Epic(Epic.getId(), Epic.getTitle(), "Epic 1", "Description 2");
        Task epic2 = new Epic(Epic.getId(), Epic.getTitle(), "Epic 2", "Description 2");

        Task subTask1 = new Subtask(Subtask.getId(), Subtask.getTitle(), "SubTask 1", "Description 2");
        Task subTask2 = new Subtask(Subtask.getId(), Subtask.getTitle(), "SubTask 2", "Description 2");

        FileBackedTasksManager.loadFromFile(file);

        List<Task> tasks = fileBackedTasksManager.getAllTask();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        FileBackedTasksManager.loadFromFile(file);
        System.out.println("Save and load multiple tasks has been successful");
    }
} 

