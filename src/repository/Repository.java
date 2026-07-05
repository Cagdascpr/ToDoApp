package repository;

import model.Priotry;
import model.Task;
import model.TaskStatus;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private List<Task> tasks = new ArrayList<>();
    private final String FILE_PATH = "tasks.txt";
    private int nextId = 1;

    public Repository() {
        loadFromFile();
    }

    public void save(Task task) {
        tasks.add(task);
        if (task.getId() >= nextId) {
            nextId = task.getId() + 1;
        }
        saveToFile();
    }

    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    public Task findById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public boolean deleteById(int id) {
        Task task = findById(id);
        if (task != null) {
            tasks.remove(task);
            saveToFile();
            return true;
        }
        return false;
    }

    public int getNextId() {
        return nextId++;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                writer.write(String.format("%d,%s,%s,%s,%s,%s\n",
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getPriotry().name(),
                        task.getStatus().name(),
                        task.getDeadline().toString()
                ));
            }
        } catch (IOException e) {
            System.out.println("Dosyaya kaydetme hatası: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 6) {
                    int id = Integer.parseInt(tokens[0]);
                    String title = tokens[1];
                    String description = tokens[2];
                    Priotry priotry = Priotry.valueOf(tokens[3]);
                    TaskStatus status = TaskStatus.valueOf(tokens[4]);
                    LocalDate deadline = LocalDate.parse(tokens[5]);

                    Task task = new Task(id, title, description, priotry, status, deadline);
                    tasks.add(task);

                    if (id >= nextId) {
                        nextId = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Dosyadan okuma hatası: " + e.getMessage());
        }
    }
}