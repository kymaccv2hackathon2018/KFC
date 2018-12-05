package team.kfc.web;

/**
 *
 */
public class RestError
{
	private final String message;


	public RestError(final String msg)
	{
		message = msg;
	}

	public String getMessage()
	{
		return message;
	}
}
