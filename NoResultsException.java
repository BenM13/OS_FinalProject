/**
Exception class for when query yields no results 
*/

public class NoResultsException extends Exception
{
    public NoResultsException()
    {
        super("WARNING: Query yielded no results");
    }

    public NoResultsException(String message)
    {
        super(message);
    }
}