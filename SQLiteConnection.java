import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.NullPointerException;

public class SQLiteConnection
{
    private final String URL;
    private final String LOG_NAME;
    private Connection conn;
    private Statement stmt;
    private ResultSet results;

    public SQLiteConnection()
    /**
    Default constructor. Sets the database URL
    to warehouse.db within the lib directory
    */
    {
        this.URL = "jdbc:sqlite:lib/warehouse.db";
        this.LOG_NAME = "sql_log.txt";
        this.conn = null;
        this.stmt = null;
        this.results = null;
    }

    public SQLiteConnection(String initURL)
    {
        this.URL = initURL;
        this.LOG_NAME = "sql_log.txt";
        this.conn = null;
        this.stmt = null;
        this.results = null;
    }

    public void createConnection(ReentrantLock rl)
    /**
    Creates a connection object to the SQLite database.
    This object is stored as an instance variable of
    the SQLiteConnection class
    */
    {
        FileOutput logger = new FileOutput(LOG_NAME);
        rl.lock();
        logger.openLog();
        try
        {
            conn = DriverManager.getConnection(URL);
            // System.out.println("Connection established.");
            logger.writeToAll(Utilities.getTimestamp() + " - Connection established.");
        } catch (SQLException e) {
            // System.out.println("Error: Unable to connect to database:");
            logger.writeToAll(Utilities.getTimestamp() +
                             " - ERROR: Unable to connect to database:");
            ///System.out.println("\t" + e.getMessage());
            logger.writeToAll("\t" + e.getMessage());
        }
        logger.closeFile();
        rl.unlock();
    }

    public void runQuery(ReentrantLock rl, String query)
    /** 
    Creates a statement objects. Runs the provided query.
    Saves result set object as instance variable of 
    SQLiteConnection class
    */
    {
        FileOutput logger = new FileOutput(LOG_NAME);
        rl.lock();
        logger.openLog();
        try
        {
            stmt = conn.createStatement();
            results = stmt.executeQuery(query);
            // System.out.println("Executing query...");
            logger.writeToAll(Utilities.getTimestamp() + " - Executing query...");
        } catch (SQLException e) {
            // System.out.println("There was a problem with the query:");
            // System.out.println("\t" + e.getMessage());
            logger.writeToAll(Utilities.getTimestamp() + 
                            " - There was a problem with the query:");
            logger.writeToAll("\t" + e.getMessage());
        }
        logger.closeFile();
        rl.unlock();
    }
    
    public void closeConnection(ReentrantLock rl)
    /** 
    Closes the statement and connection objects
    */
    {
        FileOutput logger = new FileOutput(LOG_NAME);
        rl.lock();
        logger.openLog();
        try
        {
            if ((conn == null) || (stmt == null))
            {
                throw new SQLException("Connection and/or statement object is null");
            } else {
                conn.close();
                logger.writeToAll(Utilities.getTimestamp() + " - Connection closed.");
            }
        } catch (SQLException e) {
            logger.writeToAll(Utilities.getTimestamp() + 
                             " - ERROR: Unable to closer connection");
            logger.writeToAll("\t" + e.getMessage());
        }
        logger.closeFile();
        rl.unlock();
    }

    public void printResults(ReentrantLock rl, String[] args, int threadNum)
    /** 
    Creates an object of the FileOutput class.
    If user provided an output file name, that name is passed
    to the constructor. Otherwise, default constructor is used. 
    Uses this new object to create an output file
    and write the results of the query to that file.
    If query returns no results, a warning message is displayed and
    written to the log file. 
    */
    {
        String outputFile = Utilities.getOutputFile(args, threadNum);
        rl.lock();
        FileOutput exporter;
        FileOutput logger = new FileOutput(LOG_NAME);
        if (outputFile != null)
        {
            exporter = new FileOutput(outputFile);
        } else {
            exporter = new FileOutput();
        }
        exporter.openFile();
        logger.openLog();

        try
        {
            exporter.writeToFile("id\tcourse\tstudent\temail");
            int counter = 0;
            while (results.next())
            {
                exporter.writeToFile(results.getString("id") + "\t" +
                                     results.getString("course") + "\t" +
                                     results.getString("name") + "\t" +
                                     results.getString("email"));
                counter++;
            }
            if (counter <= 0)
            {
                throw new NoResultsException();
            }
        } catch (SQLException e) {
            logger.writeToAll(Utilities.getTimestamp() +
                              " - ERROR: Unable to retrieve results");
            logger.writeToAll("\t" + e.getMessage());
        } catch (NullPointerException e) {
            logger.writeToAll(Utilities.getTimestamp() +
                              " - ERROR: Unable to retrieve results");
            logger.writeToAll("\t" + e.getMessage());
        } catch (NoResultsException e) {
            logger.writeToAll(Utilities.getTimestamp() + " - " + e.getMessage());
            logger.writeToAll("\tCommand line arguments may be incorrect");
        }

        exporter.closeFile();
        logger.closeFile();
        rl.unlock();
    }

}