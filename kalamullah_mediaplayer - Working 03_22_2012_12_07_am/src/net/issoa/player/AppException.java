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

    @Override
	public String toString() {
        return msg;
    }
}

