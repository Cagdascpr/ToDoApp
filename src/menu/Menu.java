package menu;
import Exception.TaskNotFoundException;
import model.Priotry; // Senin kaydettiğin enum adı
import model.Task;
import model.TaskStatus;
import service.Service; // Büyük harfli Service paketinden import

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Service service;
    private final Scanner scanner;

    public Menu(Service service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n--- GÖREV YÖNETİM SİSTEMİ ---");
            System.out.println("1 - Görev oluştur");
            System.out.println("2 - Görev listele");
            System.out.println("3 - Görev güncelle");
            System.out.println("4 - Görev sil");
            System.out.println("5 - Görev durumunu değiştir");
            System.out.println("6 - Önceliğe göre filtrele");
            System.out.println("7 - Duruma göre filtrele");
            System.out.println("8 - Deadline yaklaşan görevleri göster");
            System.out.println("9 - Çıkış");
            System.out.print("Seçiminiz: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: createTaskMenu(); break;
                case 2: listTasks(service.getAllTasks()); break;
                case 3: updateTaskMenu(); break;
                case 4: deleteTaskMenu(); break;
                case 5: changeStatusMenu(); break;
                case 6: filterByPriorityMenu(); break;
                case 7: filterByStatusMenu(); break;
                case 8: listTasks(service.getUpcomingTasks()); break;
                case 9:
                    System.out.println("Sistemden çıkılıyor, veriler kaydedildi!");
                    return;
                default:
                    System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }
    }

    private void createTaskMenu() {
        System.out.print("Görev Başlığı: ");
        String title = scanner.nextLine();
        System.out.print("Görev Açıklaması: ");
        String desc = scanner.nextLine();
        Priotry priotry = readPriotry();
        LocalDate deadline = readDate();

        service.createTask(title, desc, priotry, deadline);
        System.out.println("Görev başarıyla oluşturuldu.");
    }

    private void listTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Listelenecek görev bulunamadı.");
            return;
        }
        System.out.println("\n=== GÖREV LİSTESİ ===");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private void updateTaskMenu() {
        System.out.print("Güncellenecek Görev ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Yeni Başlık: ");
        String title = scanner.nextLine();
        System.out.print("Yeni Açıklama: ");
        String desc = scanner.nextLine();
        Priotry priotry = readPriotry();
        LocalDate deadline = readDate();

        try {
            service.updateTask(id, title, desc, priotry, deadline);
            System.out.println("Görev başarıyla güncellendi.");
        } catch (TaskNotFoundException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    private void deleteTaskMenu() {
        System.out.print("Silinecek Görev ID: ");
        int id = scanner.nextInt();
        try {
            service.deleteTask(id);
            System.out.println("Görev başarıyla silindi.");
        } catch (TaskNotFoundException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    private void changeStatusMenu() {
        System.out.print("Durumu değiştirilecek Görev ID: ");
        int id = scanner.nextInt();
        System.out.println("Yeni Durum Seçin (1: TODO, 2: IN_PROGRESS, 3: DONE): ");
        int statusChoice = scanner.nextInt();

        TaskStatus status = TaskStatus.TODO;
        if (statusChoice == 2) status = TaskStatus.IN_PROGRESS;
        else if (statusChoice == 3) status = TaskStatus.DONE;

        try {
            service.changeStatus(id, status);
            System.out.println("Görev durumu güncellendi.");
        } catch (TaskNotFoundException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    private void filterByPriorityMenu() {
        Priotry priotry = readPriotry();
        listTasks(service.filterByPriority(priotry));
    }

    private void filterByStatusMenu() {
        System.out.println("Filtrelenecek Durum Seçin (1: TODO, 2: IN_PROGRESS, 3: DONE): ");
        int statusChoice = scanner.nextInt();
        TaskStatus status = TaskStatus.TODO;
        if (statusChoice == 2) status = TaskStatus.IN_PROGRESS;
        else if (statusChoice == 3) status = TaskStatus.DONE;

        listTasks(service.filterByStatus(status));
    }

    private Priotry readPriotry() {
        System.out.println("Öncelik Seçin (1: LOW, 2: MEDIUM, 3: HIGH): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 2) return Priotry.MEDIUM;
        if (choice == 3) return Priotry.HIGH;
        return Priotry.LOW;
    }

    private LocalDate readDate() {
        while (true) {
            System.out.print("Son Teslim Tarihi (YIL-AY-GÜN örn: 2026-07-10): ");
            String dateStr = scanner.nextLine();
            try {
                return LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Geçersiz tarih formatı! Lütfen YYYY-MM-DD formatında girin.");
            }
        }
    }
}

