package com.ArtemBuzEd.journalApp;
import Entities.JournalEntry;
import Entities.Tag;
import Entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the journal");

        User user = null;
        JournalManager jm = null;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        File file = new File("/Users/artembuziak/consoleAppJournal/com/ArtemBuzEd/App/ user_data.json");
        if(file.exists()){
            try{
                user = objectMapper.readValue(file, User.class);
                jm = new JournalManager(user);
                System.out.println("Welcome back " + user.getName() + " to the journal");
            }catch (IOException e){
                System.out.println("Error loading user data" + e.getMessage());
            }
        } else {
            user = createNewUser();
            jm = new JournalManager(user);
        }

        System.out.println("Enter command:");

        boolean systemRun = true;
        while (systemRun){
            printMenu();
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "1" -> {
                    System.out.println("\n===== Add Journal =====");
                    System.out.println("Enter Journal title: ");
                    String journalTitle = scanner.nextLine().trim();

                    System.out.println("Enter Journal content: ");
                    String journalContent = scanner.nextLine().trim();

                    JournalEntry je = new JournalEntry(journalTitle, journalContent);

                    boolean addTag = true;
                    while (addTag){
                        System.out.println("Do you want to add tag (y/n): ");
                        String answer = scanner.nextLine().trim().toLowerCase();

                        if(answer.equals("y")){

                            System.out.println("Enter tag name");
                            String tagName = scanner.nextLine().trim();

                            TagColor color = null;
                            while (color == null) {
                                System.out.println("Choose tag color (Red/Blue/Yellow/Green/Purple)");
                                String tagColor = scanner.nextLine().trim().toUpperCase();
                                try {
                                    color = TagColor.valueOf(tagColor);
                                } catch (Exception e) {
                                    System.out.println("Invalid color. Try again.");
                                }
                            }

                            Tag newTag = new Tag(tagName, color);
                            je.addTag(newTag);
                        } else if (answer.equals("n")){
                            addTag = false;
                        } else {
                            System.out.println("Invalid option. Try again.");
                        }
                    }
                    jm.addJournal(je);
                }
                case "2" -> jm.displayUserJournals();
                case "3" -> {
                    if(user.getJournalList().isEmpty()){
                        System.out.println("There are no journal entries in the system.");
                        continue;
                    }

                    System.out.println("\n===== Update Journal =====");
                    int i = 1;
                    for (JournalEntry je : user.getJournalList()){
                        System.out.println((i) + ". Journal Title: " + je.getTitle());
                        i++;
                    }

                    System.out.println("\nEnter Journal title number: (or 0 to quit)");
                    int choice = Integer.parseInt(scanner.nextLine().trim());
                    JournalEntry je = user.getJournalEntryByIndex(choice - 1);

                    System.out.println("Current Journal Title: " + je.getTitle());
                    System.out.println("Enter new title or press Enter to skip");
                    String newTitle = scanner.nextLine().trim();



                    System.out.println("Current Content of Journal: " + je.getContent());
                    System.out.println("Enter new content or press Enter to skip");
                    String newContent = scanner.nextLine().trim();


                    System.out.println("Current Journal Tags: ");
                    if(je.getTags().isEmpty()){
                        System.out.println("No tags found.");
                    } else {
                        for (Tag tag : je.getTags()) {
                            System.out.println("- " + tag);
                        }
                    }

                    System.out.println("Do you want to update tags (y/n): ");
                    String tagsAnswer = scanner.nextLine().trim().toLowerCase();

                    jm.updateJournal(je, newTitle, newContent, tagsAnswer);

                    if(tagsAnswer.equals("y")){
                        boolean addTag = true;
                        while (addTag){
                            System.out.println("Enter tag name");
                            String tagName = scanner.nextLine().trim();

                            TagColor color = null;
                            while (color == null) {
                                System.out.println("Choose tag color (Red/Blue/Yellow/Green/Purple)");
                                String tagColor = scanner.nextLine().trim().toUpperCase();

                                try {
                                    color = TagColor.valueOf(tagColor);
                                } catch (Exception e) {
                                    System.out.println("Invalid color. Try again.");
                                }
                            }

                            Tag newTag = new Tag(tagName, color);
                            je.addTag(newTag);
                        }
                    }
                }
                case "4" -> {
                    if(jm.isJournalListEmpty())
                        continue;

                    System.out.println("\n===== Delete Journal =====");

                    int i = 1;
                    for (JournalEntry je : user.getJournalList()){
                        System.out.println(i + ". Journal Title: " + je.getTitle());
                        i++;
                    }

                    System.out.println("\nEnter Journal title number: (or 0 to quit)");
                    int deleteChoice = Integer.parseInt(scanner.nextLine().trim());

                    jm.deleteJournal(deleteChoice);
                }
                case "5" -> {
                    if(jm.isJournalListEmpty())
                        continue;


                    System.out.println("\n===== Search Journal =====");
                    System.out.println("Search by");
                    System.out.println("1. Title");
                    System.out.println("2. Content");
                    System.out.println("3. Tags");
                    System.out.println("4. Date");
                    System.out.println("Enter your choice: ");

                    String searchChoice = scanner.nextLine().trim();
                    String searchTerm = "";

                    switch (searchChoice) {
                        case "1", "2", "3" -> {
                            System.out.println("Enter Search term");
                            searchTerm = scanner.nextLine().trim();
                        }
                        case "4" -> {
                            System.out.println("Enter Search date (YYYY-MM-DD)");
                            searchTerm = scanner.nextLine().trim();
                        }
                    }

                    jm.searchJournal(searchChoice, searchTerm);
                }
                case "6" -> {
                    if(jm.isJournalListEmpty())
                        continue;

                    System.out.println("\n===== Sort Journals =====");
                    System.out.println("Sort by");
                    System.out.println("1. Title (a-z)");
                    System.out.println("2. Title (z-a)");
                    System.out.println("3. Date from newest");
                    System.out.println("4. Date from oldest");
                    System.out.println("Enter your choice: ");

                    String choice = scanner.nextLine().trim();

                    List<JournalEntry> sortedList =  jm.sortedJournals(choice);

                    System.out.println("\nEnter entry number you want to watch (or 0 to quit)");
                    int showChoice = Integer.parseInt(scanner.nextLine().trim());
                    if(showChoice > 0){
                        jm.displayJournalEntry(sortedList.get(showChoice-1));
                    }
                }
                case "7" -> {
                    System.out.println("Saving user data...");
                    jm.saveUserDate();
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

    private static User createNewUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input new user name");
        String userName = scanner.nextLine().trim();

        int birthYear;
        while (true) {
            try {
                System.out.println("Input new user birth year");
                birthYear = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid birth year.");
            }
        }

        return new User(userName, birthYear);
    }
}
