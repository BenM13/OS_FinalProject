import java.util.ArrayList;

Public class Main
{
    public static void main(String[] args)
    {
        for (String c: Utilities.getCourseList(args))
        {
            System.out.println(c);
        }
    }
}