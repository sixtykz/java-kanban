import java.util.*;

public class Manager {

    private static int id;


    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    public int generateId() {
        return id++;
    }

    public int createTask(Task task) {
        int newTaskId = generateId();
        task.setId(newTaskId);
        tasks.put(newTaskId, task);
        return newTaskId;
    }

    public int createEpic(Epic epic) {
        int newEpicId = generateId();
        epic.setId(newEpicId);
        epics.put(newEpicId, epic);
        return newEpicId;
    }

    public int createSubTask(SubTask subTask) {
        int newSubTaskId = generateId();
        subTask.setId(newSubTaskId);
        Epic epic = epics.get(subTask.getEpicId());

        if (epic != null) {
            subTasks.put(newSubTaskId, subTask);
            epic.setSubtaskIds(newSubTaskId);
            updateStatusEpic(epic);
            return newSubTaskId;
        } else {
            System.out.println("Epic not found");
            return -1;
        }

    }

    public void deleteTaskById(int Id) {
        if (tasks.containsKey(Id)) {
            tasks.remove(Id);
        } else {
            System.out.println("Task not found");
        }
    }

    public void deleteEpicById(int Id) {
        Epic epic = epics.get(Id);
        if (epic != null) {
            for (Integer subTaskId : epic.getSubtaskIds()) {
                subTasks.remove(subTaskId);
            }
            epics.remove(Id);
        } else {
            System.out.println("Epic not found");
        }

    }

    public void deleteSubTaskById(int Id) {
        SubTask subTask = subTasks.get(Id);

        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.getSubtaskIds().remove((Integer) subTask.getId());
            updateStatusEpic(epic);
            subTasks.remove(Id);
        } else {
            System.out.println("SubTask not found");
        }
    }


    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void deleteAllSubTask() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateStatusEpic(epic);
        }
    }

    public Task getTaskById(int Id) {
        return tasks.get(Id);
    }

    public Epic getEpicById(int Id) {
        return epics.get(Id);
    }

    public SubTask getSubTaskById(int Id) {
        return subTasks.get(Id);
    }

    public List<Task> getAllTask() {
        if (tasks.size() == 0) {
            System.out.println("Task list is empty");
            return Collections.emptyList();
        }
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getAllEpic() {
        if (epics.size() == 0) {
            System.out.println("Epic list is empty");
            return Collections.emptyList();
        }
        return new ArrayList<>(epics.values());
    }

    public List<SubTask> getAllSubTask() {
        if (subTasks.size() == 0) {
            System.out.println("SubTask list is empty");
            return Collections.emptyList();
        }
        return new ArrayList<>(subTasks.values());
    }

    public List<SubTask> getAllSubTaskByEpicId(int Id) {
        if (epics.containsKey(Id)) {
            List<SubTask> subTasksNew = new ArrayList<>();
            Epic epic = epics.get(Id);
            for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
                subTasksNew.add(subTasks.get(epic.getSubtaskIds().get(i)));
            }
            return subTasksNew;
        } else {
            return Collections.emptyList();
        }
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Task not found");
        }
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Task not found");
        }
    }

    public void updateStatusEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            if (epic.getSubtaskIds().size() == 0) {
                epic.setStatus(Status.NEW.getTranslation());
            } else {
                List<SubTask> subtasksNew = new ArrayList<>();
                int countDone = 0;
                int countNew = 0;

                for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
                    subtasksNew.add(subTasks.get(epic.getSubtaskIds().get(i)));
                }

                for (SubTask subTask : subtasksNew) {
                    if (subTask.getStatus().equals(Status.DONE.getTranslation())) {
                        countDone++;
                    }
                    if (subTask.getStatus().equals(Status.NEW.getTranslation())) {
                        countNew++;
                    }
                    if (subTask.getStatus().equals(Status.IN_PROGRESS.getTranslation())) {
                        epic.setStatus(Status.IN_PROGRESS.getTranslation());
                        return;
                    }
                }

                if (countDone == epic.getSubtaskIds().size()) {
                    epic.setStatus(Status.DONE.getTranslation());
                } else if (countNew == epic.getSubtaskIds().size()) {
                    epic.setStatus(Status.NEW.getTranslation());
                } else {
                    epic.setStatus(Status.IN_PROGRESS.getTranslation());
                }
            }
        } else {
            System.out.println("Epic not found");
        }
    }

    public void updateSubtask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            updateStatusEpic(epic);
        } else {
            System.out.println("SubTask not found");
        }
    }

    public void printTasks() {
        if (tasks.size() == 0) {
            System.out.println("Task List is empty");
            return;
        }
        for (Task task : tasks.values()) {
            System.out.println("Task{" +
                    "description='" + task.getDescription() + '\'' +
                    ", id=" + task.getId() +
                    ", name='" + task.getName() + '\'' +
                    ", status=" + task.getStatus() +
                    '}');
        }
    }

    public void printEpics() {
        if (epics.size() == 0) {
            System.out.println("Epic List is empty");
            return;
        }
        for (Epic epic : epics.values()) {
            System.out.println("Epic{" +
                    "subtasksIds=" + epic.getSubtaskIds() +
                    ", description='" + epic.getDescription() + '\'' +
                    ", id=" + epic.getId() +
                    ", name='" + epic.getName() + '\'' +
                    ", status=" + epic.getStatus() +
                    '}');
        }
    }

    public void printSubTask() {
        if (subTasks.size() == 0) {
            System.out.println("SubTask List is empty");
            return;
        }
        for (SubTask subTask : subTasks.values()) {
            System.out.println("Subtask{" +
                    "epicId=" + subTask.getEpicId() +
                    ", description='" + subTask.getDescription() + '\'' +
                    ", id=" + subTask.getId() +
                    ", name='" + subTask.getName() + '\'' +
                    ", status=" + subTask.getStatus() +
                    '}');
        }
    }
}