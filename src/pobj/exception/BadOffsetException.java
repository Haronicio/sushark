package pobj.exception;

public class BadOffsetException extends Exception{
    public BadOffsetException(String s)
    {
        super(s);
    }
    public BadOffsetException(String s,Throwable cause)
    {
        super(s,cause);
    }
}