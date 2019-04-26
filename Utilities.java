import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Utilities
{
    public static boolean checkExtension(String argument)
    /**
    Checks if the given STring contains a .txt or .csv extension.    
    */
    {
        boolean matches = false;
        if (argument.contains(".txt") || argument.contains(".csv"))
        {
            matches = true;
        }
        return matches;
    }

    public static ArrayList<String> getCourseList(String[] args)
    /** 
    Parses through the args array searching for course numbers. 
    Any argument that does not start with a dash '-' and does not 
    contain a file extention is assumed to be a course number. 
    Each course found is added to an ArrayList of Strings.
    Returns the completed ArrayList.
    */
    {
        ArrayList<String> courses = new ArrayList<String>();
        // StringBuilder sb = new StringBuilder();
        // boolean atBeginning = true;
        // int counter = 0; // number of courses added to string builder
        for (String s: args)
        {
            if ((s.charAt(0) != '-') && (!checkExtension(s)))
            {
                courses.add(s);
            }
        }
        return courses;
    }

    public static String buildQuery(String course)
    /**
    Inserts course number into a template query. Returns the query as a String
    */
    {
        String query = "SELECT c.student_id AS id, c.course_number AS course, " +
                       "students.student_name AS name, students.student_email AS email " +
                       "FROM (SELECT * FROM courses_taken WHERE course_number = %s) c " +
                       "JOIN students ON students.student_id = c.student_id";
        return String.format(query, course);
    }

    public static String getTimestamp()
    /**
    Returns the current date and time as a String 
    */
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = new Date();
        return formatter.format(today);
    }

    public static String getOutputFile(String[] args)
    /**
    Parses through args array searching for the first 
    argument that ends with .txt or .csv. Returns a null
    String if no filename can be found.
    */
    {
        String filename = null;
        for (String s: args)
        {
            if (s.contains(".txt") || s.contains(".csv"))
            {
                filename = s;
                break;
            }
        }
        return filename;
    }

    public static void quitProgram()
    /**
    Safely exits the program 
    */
    {
        System.out.println("Now exiting...goodbye!");
        System.exit(0);
    }

    public static void quitProgram(String message)
    {
        System.out.println(message);
        System.exit(0);
    }

}