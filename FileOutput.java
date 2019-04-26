import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileOutput
/**
Class for generating output files. This includes query
results and logs.
*/
{
    private String filename;
    private FileWriter file;
    private PrintWriter outputStream;

    public FileOutput()
    /**
    Default constructor. If the user does not provide
    a name for the output file, program will automatically
    send output to "results.csv".
    */
    {
        filename = "results.csv";
        outputStream = null;
        file = null;
    }

    public FileOutput(String initName)
    /**
    Constructor of when user provides output file name.
    Also used for creating the log file.
    */
    {
        filename = initName;
        file = null;
        outputStream = null;
    }

    public void openFile()
    /**
    Instantiates output file name. 
    Exits program if an exception is thrown while opening
    */
    {
        try
        {
            outputStream = new PrintWriter(filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file" + filename);
            Utilities.quitProgram();
        }
    }

    public void openLog()
    /**
    Instantiaties FileWriter object with append option set to true.
    Instantiaties PrintWriter ojbect using the FileWriter
    Exits program if an exception is thrown.
    */
    {
        try
        {
            file = new FileWriter(filename, true); // enable append mode
            outputStream = new PrintWriter(file);
        } catch (IOException e) {
            System.out.println("Error opening the log file");
            Utilities.quitProgram();
        }
    }

    public void closeFile()
    /**
    Closes the outputStream object 
    */
    {
        try
        {
            if (file != null)
            {
                file.close();
            }
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToFile(String line)
    /**
    Prints String to output file 
    */
    {
        outputStream.println(line);
    }
    
    public void writeToAll(String line)
    /**
    Prints String to both output file and to console 
    */
    {
        outputStream.println(line);
        System.out.println(line);
    }

    public void setFilename(String newFile)
    {
        filename = newFile;
    }

    public String getFilename()
    {
        return filename;
    }
}