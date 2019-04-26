import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        for (String c: Utilities.getCourseList(args))
        {
            System.out.println(c);
        }
    }
}