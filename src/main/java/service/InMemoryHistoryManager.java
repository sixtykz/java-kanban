package main.java.service;

import main.java.intefaces.HistoryManager;
import main.java.tasks.Task;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static class CustomLinkedList {
        private final Map<Integer, Node> table = new HashMap<>();
        private Node head;
        private Node tail;

        private void linkLast(Task task) {
            Node element = new Node();
            element.setTask(task);

            if (table.containsKey(task.getId())) {
                removeNode(table.get(task.getId()));
            }

            if (head == null) {
                tail = element;
                head = element;
                element.setNext(null);
                element.setPrev(null);
            } else {
                element.setPrev(tail);
                tail.setNext(element);
                tail = element;
            }

            table.put(task.getId(), element);
        }

        private List<Task> getTasks() {
            List<Task> result = new ArrayList<>();
            Node element = head;
            while (element != null) {
                result.add(element.getTask());
                element = element.getNext();
            }
            return result;
        }

        private void removeNode(Node node) {
            if (node != null) {
                table.remove(node.getTask().getId());
                Node prev = node.getPrev();
                Node next = node.getNext();

                if (head == node) {
                    head = node.getNext();
                }
                if (tail == node) {
                    tail = node.getPrev();
                }

                if (prev != null) {
                    prev.setNext(next);
                }

                if (next != null) {
                    next.setPrev(prev);
                }
            }
        }

        private Node getNode(int id) {
            return table.get(id);
        }
    }

    private final CustomLinkedList list = new CustomLinkedList();

    // Добавление нового просмотра задачи в историю
    @Override
    public void add(Task task) {
        list.linkLast(task);
    }

    // Удаление просмотра из истории
    @Override
    public void remove(int id) {
        list.removeNode(list.getNode(id));
    }

    // Получение истории просмотров
    @Override
    public List<Task> getHistory() {
        return list.getTasks();
    }
}

