
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListMaker
{
    static ArrayList<String> list = new ArrayList<>();

    static boolean needsToBeSaved = false;

    static String currentFileName = "";

    public static void main(String[] args)
    {
        boolean quit = false;

        while (!quit)
        {
            System.out.println("\n===== LIST MAKER =====");
            System.out.println("A - Add an item");
            System.out.println("D - Delete an item");
            System.out.println("I - Insert an item");
            System.out.println("V - View the list");
            System.out.println("M - Move an item");
            System.out.println("O - Open a list file");
            System.out.println("S - Save the list");
            System.out.println("C - Clear the list");
            System.out.println("Q - Quit");

            char choice = SafeInput.getChar("Choose an option: ");

            try
            {
                switch (choice)
                {
                    case 'A':
                        addItem();
                        break;

                    case 'D':
                        deleteItem();
                        break;

                    case 'I':
                        insertItem();
                        break;

                    case 'V':
                        viewList();
                        break;

                    case 'M':
                        moveItem();
                        break;

                    case 'O':
                        if (confirmSaveChanges())
                        {
                            openFile();
                        }
                        break;

                    case 'S':
                        saveFile();
                        break;

                    case 'C':
                        clearList();
                        break;

                    case 'Q':
                        if (confirmSaveChanges())
                        {
                            quit = true;
                        }
                        break;

                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
            catch (IOException e)
            {
                System.out.println("File error: " + e.getMessage());
            }
        }

        System.out.println("Goodbye!");
    }

    // ADD AN ITEM

    public static void addItem()
    {
        String item = SafeInput.getString("Enter item: ");

        list.add(item);

        needsToBeSaved = true;

        System.out.println("Item added successfully.");
    }

    // DELETE AN ITEM

    public static void deleteItem()
    {
        if (list.isEmpty())
        {
            System.out.println("The list is empty.");
            return;
        }

        viewList();

        int index = SafeInput.getInt("Enter item number to delete: ");

        if (index >= 1 && index <= list.size())
        {
            list.remove(index - 1);

            needsToBeSaved = true;

            System.out.println("Item deleted successfully.");
        }
        else
        {
            System.out.println("Invalid item number.");
        }
    }

    // INSERT AN ITEM

    public static void insertItem()
    {
        viewList();

        int index = SafeInput.getInt("Enter position to insert item: ");

        if (index >= 1 && index <= list.size() + 1)
        {
            String item = SafeInput.getString("Enter item: ");

            list.add(index - 1, item);

            needsToBeSaved = true;

            System.out.println("Item inserted successfully.");
        }
        else
        {
            System.out.println("Invalid position.");
        }
    }

    // VIEW THE LIST

    public static void viewList()
    {
        System.out.println("\n===== YOUR LIST =====");

        if (list.isEmpty())
        {
            System.out.println("The list is empty.");
            return;
        }

        for (int i = 0; i < list.size(); i++)
        {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    // MOVE AN ITEM

    public static void moveItem()
    {
        if (list.isEmpty())
        {
            System.out.println("The list is empty.");
            return;
        }

        viewList();

        int from = SafeInput.getInt("Enter item number to move: ");

        if (from < 1 || from > list.size())
        {
            System.out.println("Invalid item number.");
            return;
        }

        int to = SafeInput.getInt("Enter new position: ");

        if (to < 1 || to > list.size())
        {
            System.out.println("Invalid new position.");
            return;
        }

        if (from == to)
        {
            System.out.println("The item is already in that position.");
            return;
        }

        String item = list.remove(from - 1);

        list.add(to - 1, item);

        needsToBeSaved = true;

        System.out.println("Item moved successfully.");
    }

    // CLEAR THE LIST

    public static void clearList()
    {
        if (list.isEmpty())
        {
            System.out.println("The list is already empty.");
            return;
        }

        list.clear();

        needsToBeSaved = true;

        System.out.println("List cleared successfully.");
    }

    // SAVE FILE

    public static void saveFile() throws IOException
    {
        if (currentFileName.isEmpty())
        {
            currentFileName = SafeInput.getString("Enter filename: ");

            if (!currentFileName.toLowerCase().endsWith(".txt"))
            {
                currentFileName += ".txt";
            }
        }

        Path path = Paths.get(currentFileName);

        Files.write(path, list, StandardCharsets.UTF_8);

        needsToBeSaved = false;

        System.out.println("File saved successfully: " + currentFileName);
    }

    // OPEN FILE

    public static void openFile() throws IOException
    {
        String filename = SafeInput.getString("Enter filename to open: ");

        if (!filename.toLowerCase().endsWith(".txt"))
        {
            filename += ".txt";
        }

        Path path = Paths.get(filename);

        ArrayList<String> loadedList =
                new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

        list.clear();

        list.addAll(loadedList);

        currentFileName = filename;

        needsToBeSaved = false;

        System.out.println("File loaded successfully: " + currentFileName);
    }

    // CHECK FOR UNSAVED CHANGES

    public static boolean confirmSaveChanges() throws IOException
    {
        if (!needsToBeSaved)
        {
            return true;
        }

        System.out.println("\nYou have unsaved changes.");

        char answer = SafeInput.getChar(
                "Save changes? (Y = Yes, N = No, C = Cancel): ");

        if (answer == 'Y')
        {
            saveFile();

            return !needsToBeSaved;
        }
        else if (answer == 'N')
        {
            return true;
        }
        else
        {
            System.out.println("Operation cancelled.");

            return false;
        }
    }
}
