package com.ArtemBuzEd.journalApp;

import Entities.User;

import java.util.Scanner;

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

        JournalManager jm = new JournalManager(user1);
        boolean systemRun = true;
        while (systemRun){
            printMenu();
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "1" -> jm.addJournal(scanner);
                case "2" -> jm.displayUserJournals();
                case "3" -> jm.updateJournal(scanner);
                case "4" -> jm.deleteJournal(scanner);
                case "5" -> jm.searchJournal(scanner);
                case "6" -> jm.sortJournals(scanner);
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
