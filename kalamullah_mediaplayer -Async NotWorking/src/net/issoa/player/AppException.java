package net.issoa.player;

public class AppException extends Exception
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String msg = null;

    public AppException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return msg;
    }
}

