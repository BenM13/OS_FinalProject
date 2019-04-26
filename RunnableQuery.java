public class RunnableQuery implements Runnable
{
    private Thread t;
    private String threadName;
    private String[] arguments;

    public RunnableQuery(String name, String[] args)
    {
        threadName = name;
        arguments = args;
        System.out.println("Creating " + threadName);
    }

    public void run()
    {
        String query = Utilities.buildQuery(threadName);
        SQLiteConnection db = new SQLiteConnection();

        db.createConnection();
        db.runQuery(query);
        db.printResults(arguments);
        db.closeConnection();
    }

    public void start()
    {
        System.out.println("Starting " + threadName);
        if (t == null) 
        {
            t = new Thread (this, threadName);
            t.start();
        }
    }
}