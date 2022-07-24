import java.util.HashMap;
import java.util.Scanner;

public class MenuTest {

    public static void main(String[] args) {
        HashMap<String, String> menu = new HashMap<>();

        menu.put("add", "Do Addition");
        menu.put("sub", "Do Subtraction");

        int value = 0;
        String choice = null;
        do {
            System.out.println("Value is currently: " + value);
            choice = getUserSelection("Let's do math (x to quit): ", menu);

            if (choice.equals("add")) {
                value += getAmount();
            } else if (choice.equals("sub")) {
                value -= getAmount();
            }
        } while (choice != null);

    }

    private static int getAmount() {
        HashMap<String, String> numberMenu = new HashMap<>();
        numberMenu.put("1", "One");
        numberMenu.put("5", "Five");
        numberMenu.put("10", "Ten");
        numberMenu.put("100", "One Hundred");
        String num = getUserSelection("How much:", numberMenu);
        if (num == null) {
            System.out.println("You must choose a number.");
            return getAmount();
        }
        return Integer.parseInt(num);
    }


    static Scanner in = new Scanner(System.in);

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

}
