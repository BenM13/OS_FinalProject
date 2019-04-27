import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        int num = 1;
        for (String c: Utilities.getCourseList(args))
        {
            RunnableQuery t = new RunnableQuery(c, args, num);
            t.start();
            num++;
        }
    }
}