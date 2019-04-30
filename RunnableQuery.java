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
        System.out.println("Creating " + threadName);
    }

    public void run()
    {
        String query = Utilities.buildQuery(threadName);
        ReentrantLock rl = new ReentrantLock();
        SQLiteConnection db = new SQLiteConnection();

        db.createConnection(rl, threadName);
        db.runQuery(rl, query, threadName);
        db.printResults(rl, arguments, threadNum, threadName);
        db.closeConnection(rl, threadName);    
    }

    public void start()
    {
        System.out.println("Starting " + threadName);
        if (t == null) 
        {
            t = new Thread (this, threadName);
            t.start();
        }
        System.out.println("Exiting " + threadName);
    }
}