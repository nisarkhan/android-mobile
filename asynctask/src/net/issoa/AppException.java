package net.issoa;

public class AppException extends Exception
{
    public String msg = null;

    public AppException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
	public String toString() {
        return msg;
    }
}

