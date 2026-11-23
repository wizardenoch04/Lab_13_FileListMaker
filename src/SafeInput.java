
import java.util.Scanner;

public class SafeInput
{
    private static final Scanner in = new Scanner(System.in);

    public static String getString(String prompt)
    {
        System.out.print(prompt);
        return in.nextLine();
    }

    public static int getInt(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            if (in.hasNextInt())
            {
                int value = in.nextInt();
                in.nextLine();
                return value;
            }
            else
            {
                System.out.println("Invalid input. Please enter a whole number.");
                in.nextLine();
            }
        }
    }

    public static char getChar(String prompt)
    {
        System.out.print(prompt);
        return in.nextLine().trim().toUpperCase().charAt(0);
    }
}
