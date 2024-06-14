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
        manager.createTask(new Task(1, "Описание-1", "Task-1", Status.NEW));
        manager.createTask(new Task(1, "Описание-2", "Task-2", Status.NEW));
        manager.createEpic(new Epic(3, "Описание-1", "Epic-1", Status.NEW));
        manager.createEpic(new Epic(3, "Описание-1", "Epic-2", Status.NEW));
        manager.createSubTask(new Subtask(3, "Описание-1", "Subtask-1", Status.NEW));
        manager.createSubTask(new Subtask(3, "Описание-2", "Subtask-2", Status.NEW));
        manager.createSubTask(new Subtask(3, "Описание-3", "Subtask-3", Status.NEW));

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

// Создаем задачи, подзадачи и эпики
        Task task1 = new Task(1, "Task 1", "Description 1", Status.IN_PROGRESS);
        Task task2 = new Task(2, "Task 2", "Description 2", Status.DONE);

        Epic epic1 = new Epic(3, "Epic 1", "Description 3", Status.IN_PROGRESS);
        Epic epic2 = new Epic(4, "Epic 2", "Description 4", Status.DONE);

        Subtask subtask1 = new Subtask(5, "Subtask 1", "Description 5", Status.IN_PROGRESS);
        Subtask subtask2 = new Subtask(6, "Subtask 2", "Description 6", Status.DONE);

// Записываем в файл
        File file = File.createTempFile("tasks", ".csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);


// Загружаем задачи из файла и выводим их в консоль
        FileBackedTasksManager.loadFromFile(file);
        List<Task> tasks = fileBackedTasksManager.getAllTask();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
    }
}

