package test;

import main.java.service.FileBackedTasksManager;
import main.java.service.InMemoryTaskManager;
import main.java.tasks.Status;
import main.java.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Nested
    class TaskManagerTest {
        private InMemoryTaskManager manager;

        @BeforeEach
        public void setUp() {
            manager = new InMemoryTaskManager();
        }

        @AfterEach
        public void tearDown() {
            manager.deleteAllTasks();
            manager.deleteAllEpics();
            manager.deleteAllSubTask();
        }

        @Test
        public void testCreateAndGetTask() {
            Task task = new Task(1, "Test Task", "Description", Status.NEW);
            manager.createTask(task);
            Task task1 = manager.getTaskById(1);
        }

        @Test
        public void testDeleteTask() {
            manager.createTask(new Task(1, "Task to Delete", "Description", Status.NEW));
            manager.deleteTaskById(1);
            assertNull(manager.getTaskById(1));
        }

        @Test
        public void testGetHistory() {
            Task task = new Task(1, "History Task", "Description", Status.NEW);
            manager.createTask(task);
            manager.getTaskById(1);
            List<Task> history = manager.getHistory();
        }

        @Test
        public void testSaveAndLoadFromFile() throws IOException {
            File file = File.createTempFile("test_tasks", ".csv");
            manager.createTask(new Task(1, "File Task", "Description", Status.NEW));
            InMemoryTaskManager loadedManager = FileBackedTasksManager.loadFromFile(file);
        }
    }
