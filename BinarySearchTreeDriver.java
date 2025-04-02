import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Driver program for the Binary Search Tree
 * Handles user input and file processing
 */
public class BinarySearchTreeDriver {
    
    /**
     * Main method to run the program
     * 
     * @param args Command line arguments (input file name)
     */
    public static void main(String[] args) {
        // Check if the correct number of arguments are provided
        if (args.length != 1) {
            System.out.println("Usage: java BinarySearchTreeDriver <input-file>");
            System.exit(1);
        }
        
        String filename = args[0];
        Scanner keyboard = new Scanner(System.in);
        
        // Ask user for data type
        System.out.print("Enter list type (i - int, d - double, s - string): ");
        String type = keyboard.nextLine().trim().toLowerCase();
        
        // Create and process appropriate tree based on type
        if (type.equals("i")) {
            processBST(filename, keyboard, Integer.class);
        } else if (type.equals("d")) {
            processBST(filename, keyboard, Double.class);
        } else if (type.equals("s")) {
            processBST(filename, keyboard, String.class);
        } else {
            System.out.println("Invalid type. Exiting.");
            System.exit(1);
        }
        
        keyboard.close();
    }
    
    /**
     * Process BST operations based on data type
     * 
     * @param filename Input file name
     * @param keyboard Scanner for user input
     * @param clazz Class type for generic operations
     */
    private static <T extends Comparable<T>> void processBST(String filename, 
                                                            Scanner keyboard,
                                                            Class<T> clazz) {
        BinarySearchTree<T> bst = new BinarySearchTree<>();
        
        // Try to read data from file and build tree
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        T value = parseValue(line, clazz);
                        if (value != null) {
                            bst.insert(value);
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing value: " + line);
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + filename);
            System.exit(1);
        }
        
        // Display available commands
        System.out.println("Commands:");
        System.out.println("(i) - Insert Item");
        System.out.println("(d) - Delete Item");
        System.out.println("(p) - Print Tree");
        System.out.println("(r) - Retrieve Item");
        System.out.println("(l) - Count Leaf Nodes");
        System.out.println("(s) - Find Single Parents");
        System.out.println("(c) - Find Cousins");
        System.out.println("(q) - Quit program");
        
        // Process user commands
        boolean quit = false;
        while (!quit) {
            System.out.print("Enter a command: ");
            String command = keyboard.nextLine().trim().toLowerCase();
            
            try {
                switch (command) {
                    case "i":  // Insert
                        bst.inOrder();
                        if (clazz == Integer.class || clazz == Double.class) {
                            System.out.print("Enter a number to insert: ");
                        } else {
                            System.out.print("Enter a string to insert: ");
                        }
                        String insertValue = keyboard.nextLine().trim();
                        T valueToInsert = parseValue(insertValue, clazz);
                        bst.insert(valueToInsert);
                        bst.inOrder();
                        break;
                        
                    case "d":  // Delete
                        bst.inOrder();
                        if (clazz == Integer.class || clazz == Double.class) {
                            System.out.print("Enter a number to delete: ");
                        } else {
                            System.out.print("Enter a string to delete: ");
                        }
                        String deleteValue = keyboard.nextLine().trim();
                        T valueToDelete = parseValue(deleteValue, clazz);
                        bst.delete(valueToDelete);
                        bst.inOrder();
                        break;
                        
                    case "p":  // Print
                        bst.inOrder();
                        break;
                        
                    case "r":  // Retrieve
                        bst.inOrder();
                        if (clazz == Integer.class || clazz == Double.class) {
                            System.out.print("Enter a number to search: ");
                        } else {
                            System.out.print("Enter a string to search: ");
                        }
                        String searchValue = keyboard.nextLine().trim();
                        T valueToSearch = parseValue(searchValue, clazz);
                        boolean found = bst.retrieve(valueToSearch);
                        if (found) {
                            System.out.println("Item is present in the tree");
                        } else {
                            System.out.println("Item is not present in the tree");
                        }
                        break;
                        
                    case "l":  // Count Leaf Nodes
                        bst.getNumLeafNodes();
                        break;
                        
                    case "s":  // Find Single Parents
                        bst.getSingleParent();
                        break;
                        
                    case "c":  // Find Cousins
                        bst.inOrder();
                        if (clazz == Integer.class) {
                            System.out.print("Enter a number: ");
                        } else if (clazz == Double.class) {
                            System.out.print("Enter a number: ");
                        } else {
                            System.out.print("Enter a string: ");
                        }
                        String cousinValue = keyboard.nextLine().trim();
                        T valueToFindCousins = parseValue(cousinValue, clazz);
                        bst.getCousins(valueToFindCousins);
                        break;
                        
                    case "q":  // Quit
                        quit = true;
                        break;
                        
                    default:
                        System.out.println("Invalid command. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error processing command: " + e.getMessage());
            }
        }
    }
    
    /**
     * Parse string value to the appropriate type
     * 
     * @param value String value to parse
     * @param clazz Class type to parse to
     * @return Parsed value of type T
     */
    private static <T extends Comparable<T>> T parseValue(String value, Class<T> clazz) {
        try {
            if (clazz == Integer.class) {
                return (T) Integer.valueOf(value);
            } else if (clazz == Double.class) {
                return (T) Double.valueOf(value);
            } else {
                return (T) value;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing value: " + value);
            throw e;
        }
    }
}
