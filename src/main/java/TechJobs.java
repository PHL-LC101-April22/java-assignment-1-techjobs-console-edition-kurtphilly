
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    static Scanner in = new Scanner(System.in);

    private static HashMap<String, String> getColumnChoices() {
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");
        return columnChoices;
    }

    private static HashMap<String, String> getActionChoices() {
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");
        return actionChoices;
    }

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = getColumnChoices();

        // Top-level menu options
        HashMap<String, String> actionChoices = getActionChoices();
        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {
            String actionChoice = getUserSelection("View jobs by (type 'x' to quit):", actionChoices);

            if (actionChoice == null) {
                break;
            } else if (actionChoice.equals("list")) {
                ShowList(columnChoices);
            } else { // choice is "search"
                ShowSearch(columnChoices);
            }
        }
    }

    private static void ShowSearch(HashMap<String, String> columnChoices) {
        // How does the user want to search (e.g. by skill or employer)
        String searchField = getUserSelection("Search by:", columnChoices);

        // What is their search term?
        System.out.println("\nSearch term:");
        String searchTerm = in.nextLine();

        if (searchField.equals("all")) {
            printJobs(JobData.findByValue(searchTerm));
        } else {
            printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
        }
    }

    private static void ShowList(HashMap<String, String> columnChoices) {
        String columnChoice = getUserSelection("List", columnChoices);

        if (columnChoice.equals("all")) {
            printJobs(JobData.findAll());
        } else {
            ArrayList<String> results = JobData.findAll(columnChoice);
            System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

            // Print list of skills, employers, etc
            for (String item : results) {
                System.out.println(item);
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {
            // View jobs by (type 'x' to quit):
            System.out.println("\n" + menuHeader);

            // Print available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
                // 0 - Search
                // 1 - List
            }

            if (in.hasNextInt()) {
                choiceIdx = in.nextInt();
                in.nextLine();
            } else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");
                if (shouldQuit) {
                    return null;
                }
            }

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        if (someJobs.isEmpty()) {
            System.out.print("No Results");
        }
        // A, B, C, D (size: 4) 4 + 1 = 5
        // 0  1  2  3
       for (int ix = 0; ix < someJobs.size(); ix++) {
           HashMap<String, String> row = someJobs.get(ix);
           System.out.println("\n*****");
           for (String key : row.keySet()) {
               String value = row.get(key);
               System.out.println(key + ": " + value);
           }
           System.out.println("*****");
       }
    }
}
