package service;

import Exception.TaskNotFoundException;
import model.Priotry;
import model.Task;
import model.TaskStatus;
import repository.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void createTask(String title, String description, Priotry priority, LocalDate deadline) {
        int id = repository.getNextId();
        Task task = new Task(id, title, description, priority, deadline);
        repository.save(task);
    }

    // Tüm Görevleri Listeleme
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public void updateTask(int id, String title, String description, Priotry priority, LocalDate deadline) throws TaskNotFoundException {
        Task task = repository.findById(id);
        if (task == null) {
            throw new TaskNotFoundException("Güncellenmek istenen " + id + " ID'li görev bulunamadı!");
        }
        task.setTitle(title);
        task.setDescription(description);
        task.setPriotry(priority);
        task.setDeadline(deadline);
        repository.saveToFile(); // Güncelleme sonrası dosyayı yenile
    }


    public void deleteTask(int id) throws TaskNotFoundException {
        boolean deleted = repository.deleteById(id);
        if (!deleted) {
            throw new TaskNotFoundException("Silinmek istenen " + id + " ID'li görev bulunamadı!");
        }
    }


    public void changeStatus(int id, TaskStatus status) throws TaskNotFoundException {
        Task task = repository.findById(id);
        if (task == null) {
            throw new TaskNotFoundException("Durumu değiştirilmek istenen " + id + " ID'li görev bulunamadı!");
        }
        task.setStatus(status);
        repository.saveToFile();
    }

    public List<Task> filterByPriority(Priotry priority) {
        List<Task> filtered = new ArrayList<>();
        for (Task task : repository.findAll()) {
            if (task.getPriotry() == priority) {
                filtered.add(task);
            }
        }
        return filtered;
    }


    public List<Task> filterByStatus(TaskStatus status) {
        List<Task> filtered = new ArrayList<>();
        for (Task task : repository.findAll()) {
            if (task.getStatus() == status) {
                filtered.add(task);
            }
        }
        return filtered;
    }

    public List<Task> getUpcomingTasks() {
        List<Task> upcoming = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Task task : repository.findAll()) {
            // Task DONE olmadıysa ve tarihi bugünden sonraysa aradaki gün farkına bakıyoruz
            if (task.getStatus() != TaskStatus.DONE) {
                long daysLeft = ChronoUnit.DAYS.between(today, task.getDeadline());
                if (daysLeft >= 0 && daysLeft <= 3) {
                    upcoming.add(task);
                }
            }
        }
        return upcoming;
    }
}
