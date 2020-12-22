package com.company;

public class BadFrameFormatException extends Exception{
    public BadFrameFormatException(String s)
    {
        super(s);
    }
    public BadFrameFormatException(String s, Throwable cause)
    {
        super(s,cause);
    }
}