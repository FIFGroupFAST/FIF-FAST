package co.id.fifgroup.basicsetup.test;

public class TestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4879698144400425343L;

	public TestException() {
		super();
	}

	public TestException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TestException(String message, Throwable cause) {
		super(message, cause);
	}

	public TestException(String message) {
		super(message);
	}

	public TestException(Throwable cause) {
		super(cause);
	}

}
