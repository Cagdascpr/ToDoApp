package model;
import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private Priotry priotry;
    private TaskStatus status;
    private LocalDate deadline;


    public Task(int id, String title, String description, Priotry priotry, LocalDate deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priotry = priotry;
        this.status = TaskStatus.TODO;
        this.deadline = deadline;

    }

    public Task(int id, String title, String description, Priotry priotry,TaskStatus status, LocalDate deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priotry = priotry;
        this.status = status;
        this.deadline = deadline;

    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priotry getPriotry() {
        return priotry;
    }

    public void setPriotry(Priotry priotry) {
        this.priotry = priotry;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Task{" +
                "ID=" + id +
                ", Başlık='" + title + '\'' +
                ", Açıklama='" + description + '\'' +
                ", Öncelik=" + priotry +
                ", Durum=" + status +
                ", Deadline=" + deadline +
                '}';
    }
}
