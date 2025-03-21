package com.ArtemBuzEd.journalApp;

import Comparators.DateComparator;
import Comparators.TitleComparator;
import Entities.JournalEntry;
import Entities.Tag;
import Entities.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JournalManager {
    private final User user;

    public JournalManager(User user) {
        this.user = user;
    }
    public void displayUserJournals() {
        if(user.getJournalList().isEmpty()){
            System.out.println("No journal found");
            return;
        }

        for(JournalEntry je : user.getJournalList()){
            displayJournalEntry(je);
        }
    }
    public void displayJournalEntry(JournalEntry entry) {
        System.out.println("\n===" + entry.getTitle() + "===");
        System.out.println("Date: " + entry.getWroteDate());
        System.out.println("Time: " + entry.getWroteTime().getHour() + ":" + entry.getWroteTime().getMinute());

        System.out.println("\nTags: ");
        if(entry.getTags().isEmpty()) {
            System.out.println("None");
        } else {
            for(Tag tag : entry.getTags()) {
                System.out.println("- " + tag);
            }
        }

        System.out.println("\nContent: ");
        System.out.println(entry.getContent());
        System.out.println("\n==================================");

    }
    public void addJournal(Scanner scanner){
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
                addTagToEntry(scanner, je);
            } else if (answer.equals("n")){
                addTag = false;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }

        user.addJournalEntry(je);
        System.out.println("Journal added successfully!");
    }

    public void addTagToEntry(Scanner scanner, JournalEntry je){
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

    public void updateJournal(Scanner scanner){
        var jeList = user.getJournalList();
        if(jeList.isEmpty()){
            System.out.println("No journal found.");
            return;
        }
        System.out.println("\n===== Update Journal =====");
        int i = 0;
        for (JournalEntry je : jeList){
            ++i;
            System.out.println((i) + ". Journal Title: " + je.getTitle());
        }

        System.out.println("\nEnter Journal title number: (or 0 to quit)");
        int choice = Integer.parseInt(scanner.nextLine().trim());

        if(choice > 0 && choice <= jeList.size()){
            JournalEntry je = jeList.get(choice - 1);

            System.out.println("Current Journal Title: " + je.getTitle());
            System.out.println("Enter new title or press Enter to skip");
            String newTitle = scanner.nextLine().trim();
            if(!newTitle.isEmpty()){
                je.setTitle(newTitle);
            }

            System.out.println("Current Content of Journal: " + je.getContent());
            System.out.println("Enter new content or press Enter to skip");
            String newContent = scanner.nextLine().trim();
            if(!newContent.isEmpty()){
                je.setContent(newContent);
            }

            System.out.println("Current Journal Tags: ");
            if(je.getTags().isEmpty()){
                System.out.println("No tags found.");
            } else {
                for (Tag tag : je.getTags()) {
                    System.out.println("- " + tag);
                }
            }

            System.out.println("Do you want to update tags (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if(answer.equals("y")){
                je.getTags().clear();
                boolean addTag = true;
                while (addTag) {
                    addTagToEntry(scanner, je);
                    System.out.println("Do you want to add tag (y/n): ");
                    answer = scanner.nextLine().trim().toLowerCase();
                    if(!answer.equals("y")){
                        addTag = false;
                    }
                }
            }
        }

    }

    public void deleteJournal(Scanner scanner){
        if(user.getJournalList().isEmpty()){
            System.out.println("No journal found.");
            return;
        }

        System.out.println("\n===== Delete Journal =====");
        for (int i = 0; i < user.getJournalList().size(); i++){
            System.out.println((i + 1) + ". Journal Title: " + user.getJournalList().get(i).getTitle());
        }

        System.out.println("\nEnter Journal title number: (or 0 to quit)");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice > 0 && choice <= user.getJournalList().size()) {
                user.getJournalList().remove(choice - 1);
                System.out.println("Journal was deleted successfully!");
            } else if(choice != 0){
                System.out.println("Invalid option. Try again.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " [deleteJournal func]");
        }
    }

    public void displayJournalSearchResult(List<JournalEntry> journalList){
        if(journalList.isEmpty()){
            System.out.println("No matching journals was found.");
            return;
        }

        System.out.println("\n===== Journals =====");
        for(int i = 0; i < journalList.size(); i++){
            JournalEntry je = journalList.get(i);
            System.out.println((i) + ". Journal Title: " + je.getTitle() + " (" + je.getWroteDate() + ")");
        }

        System.out.println("\nEnter Journal title number: (or 0 to quit)");
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine().trim());

        if(choice > 0 && choice <= journalList.size()){
            displayJournalEntry(journalList.get(choice - 1));
        }
    }
    public void searchJournal(Scanner scanner){
        if(user.getJournalList().isEmpty()){
            System.out.println("No journal found.");
            return;
        }

        System.out.println("\n===== Search Journal =====");
        System.out.println("Search by");
        System.out.println("1. Title");
        System.out.println("2. Content");
        System.out.println("3. Tags");
        System.out.println("4. Date");
        System.out.println("Enter your choice: ");

        String choice = scanner.nextLine().trim();
        List<JournalEntry> journalListOfFounded = new ArrayList<>();

        switch (choice) {
            case "1":
                System.out.println("Enter Journal Title: ");
                String searchableTitle = scanner.nextLine().trim().toLowerCase();
                for(JournalEntry je : user.getJournalList()){
                    if(je.getTitle().toLowerCase().contains(searchableTitle)){
                        journalListOfFounded.add(je);
                    }
                }
                break;
                case "2":
                    System.out.println("Enter Journal Content: ");
                    String searchableContent = scanner.nextLine().trim().toLowerCase();
                    for(JournalEntry je : user.getJournalList()){
                        if(je.getContent().toLowerCase().contains(searchableContent)){
                            journalListOfFounded.add(je);
                        }
                    }
                    break;
            case "3":
                System.out.println("Enter Journal Entities.Tag: ");
                String searchableTag = scanner.nextLine().trim().toLowerCase();
                for(JournalEntry je : user.getJournalList()){
                    for(Tag tag : je.getTags()){
                        if(tag.getName().toLowerCase().contains(searchableTag)){
                            journalListOfFounded.add(je);
                            break;
                        }
                    }
                }
                break;
            case "4":
                System.out.println("Enter Journal Date: ");
                String searchableDate = scanner.nextLine().trim().toLowerCase();
                LocalDate searchDate = LocalDate.parse(searchableDate);

                for(JournalEntry je : user.getJournalList()){
                    if(je.getWroteDate().equals(searchDate)){
                        journalListOfFounded.add(je);
                    }
                }
                break;
                default:
                    System.out.println("Invalid choice");
                    break;
        }
        displayJournalSearchResult(journalListOfFounded);
    }

    public void sortJournals(Scanner scanner){
        System.out.println("\n===== Sort Journals =====");
        System.out.println("Sort by");
        System.out.println("1. Title (a-z)");
        System.out.println("2. Title (z-a)");
        System.out.println("3. Date from newest");
        System.out.println("4. Date from oldest");
        System.out.println("Enter your choice: ");

        String choice = scanner.nextLine().trim();

        List<JournalEntry> sortedList = new ArrayList<>(user.getJournalList());
        switch (choice) {
            case "1":
                sortedList.sort(new TitleComparator());
                break;
            case "2":
                sortedList.sort(new TitleComparator().reversed());
                break;
            case "3":
                sortedList.sort(new DateComparator());
                break;
            case "4":
                sortedList.sort(new DateComparator().reversed());
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }
        System.out.println("\n===== Sort Journals =====");
        int i = 0;
        for(JournalEntry je : sortedList){
            ++i;
            System.out.println((i) + ". " + je.getTitle() + " (" + je.getWroteDate() + ")");
        }

        System.out.println("\nEnter entry number you want to watch (or 0 to quit)");
        int jeChoice = Integer.parseInt(scanner.nextLine().trim());

        if(jeChoice > 0 && jeChoice <= user.getJournalList().size()){
            displayJournalEntry(sortedList.get(jeChoice - 1));
        }
    }

}
