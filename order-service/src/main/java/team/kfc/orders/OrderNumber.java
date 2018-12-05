package team.kfc.orders;

/**
 *
 */
public class OrderNumber
{
	private static final int ORDER_NUMBER_LENGTH = 8;
	private final String unpaddedOrderNumber;
	private final String zeroPaddedOrderNumber;

	public OrderNumber(final String number)
	{
		unpaddedOrderNumber = stripLeadingZeros(validated(number));
		zeroPaddedOrderNumber = pad(unpaddedOrderNumber);
	}

	private String validated(final String n)
	{
		if (n == null)
		{
			throw new IllegalArgumentException("Order number cannot be null");
		}
		final String number = n.trim();
		if (number.isEmpty())
		{
			throw new IllegalArgumentException("Order number cannot be empty");
		}
		try
		{
			Integer.parseInt(number);
		}
		catch (final NumberFormatException e)
		{
			throw new IllegalArgumentException("Invalid order number value: " + number + ". Must be numeric.");
		}
		if (number.length() > ORDER_NUMBER_LENGTH)
		{
			throw new IllegalArgumentException("Invalid order number value: " + number + ". Cannot be longer than 8 digits");
		}
		return number;
	}

	public String asString()
	{
		return unpaddedOrderNumber;
	}

	public String asZeroPaddedString()
	{
		return zeroPaddedOrderNumber;
	}

	private String pad(final String value)
	{
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i + value.length() < ORDER_NUMBER_LENGTH; i++) builder.append('0');
		return builder.append(value).toString();
	}

	private String stripLeadingZeros(final String str)
	{
		return str.startsWith("0")
				? stripLeadingZeros(str.substring(1))
				: str;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o != null && getClass() == o.getClass())
		{
			final OrderNumber that = (OrderNumber) o;
			return unpaddedOrderNumber.equals(that.unpaddedOrderNumber);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return unpaddedOrderNumber.hashCode();
	}

	@Override
	public String toString()
	{
		return "Order #" + unpaddedOrderNumber;
	}
}
