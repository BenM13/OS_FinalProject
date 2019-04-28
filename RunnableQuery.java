import java.util.concurrent.locks.ReentrantLock;

public class RunnableQuery implements Runnable
{
    private Thread t;
    private String threadName;
    private int threadNum;
    private String[] arguments;

    public RunnableQuery(String name, String[] args, int num)
    {
        threadName = name;
        arguments = args;
        threadNum = num;
        System.out.println("Creating thread: " + threadName);
    }

    public void run()
    {
        String query = Utilities.buildQuery(threadName);
        ReentrantLock rl = new ReentrantLock();
        SQLiteConnection db = new SQLiteConnection();

        db.createConnection(rl);
        db.printResults(rl, arguments, threadNum);
        db.closeConnection(rl);    
    }

    public void start()
    {
        System.out.println("Starting thread: " + threadName);
        if (t == null) 
        {
            t = new Thread (this, threadName);
            t.start();
        }
    }
}