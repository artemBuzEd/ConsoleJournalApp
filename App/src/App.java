import java.util.Scanner;
import com.google.gson.*;

//створити пакет com.ArtemBuz.JournalApp
//створювати обєкт journalManager, передававати туди юзера в конструктор
//Не передавати сканер, в параметри, а передавати результат зчитаний
public class App {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the journal <name>");
        System.out.println("Please enter the name of the journal you would like to view:");
        User user1 = new User("Zaglushka", 2006);
        System.out.println("Enter command:");

        boolean systemRun = true;
        while (systemRun){
            printMenu();
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "1" -> JournalManager.addJournal(scanner, user1);
                case "2" -> JournalManager.displayUserJournals(user1);
                case "3" -> JournalManager.updateJournal(scanner, user1);
                case "4" -> JournalManager.deleteJournal(scanner, user1);
                case "5" -> JournalManager.searchJournal(scanner, user1);
                case "6" -> JournalManager.sortJournals(scanner, user1.getJournalList());
                case "7" -> {
                    System.out.println("Journal closed. Goodbye!");
                    systemRun = false;
                }
                default -> System.out.println("Invalid command. Try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu(){
        System.out.println("\n===== Journal Menu =====");
        System.out.println("1. Add Journal entry");
        System.out.println("2. View journal entry");
        System.out.println("3. Update journal entry");
        System.out.println("4. Delete journal entry");
        System.out.println("5. Search journals");
        System.out.println("6. Sort journals");
        System.out.println("7. Exit");
        System.out.println("Enter your choice:");
    }
}
