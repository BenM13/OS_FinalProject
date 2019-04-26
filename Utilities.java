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
        if (argument.clontains(".txt") || argument.contains(".csv"))
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

}