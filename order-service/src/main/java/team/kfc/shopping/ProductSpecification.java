package team.kfc.shopping;

/**
 *
 */
public class ProductSpecification
{
	private String store = "electronics";
	private String productName = "";
	private String category = "";
	private int minPrice = -1;
	private int maxPrice = -1;

	public String getStore()
	{
		return store;
	}

	public String getProductName()
	{
		return productName;
	}

	public String getCategory()
	{
		return category;
	}

	public int getMinPrice()
	{
		return minPrice;
	}

	public int getMaxPrice()
	{
		return maxPrice;
	}

	public ProductSpecification setStore(final String storeName)
	{
		store = storeName;
		return this;
	}

	public ProductSpecification setProductName(final String name)
	{
		productName = name;
		return this;
	}

	public ProductSpecification setCategory(final String name)
	{
		category = name;
		return this;
	}

	public ProductSpecification setMinPrice(final int limit)
	{
		minPrice = limit;
		return this;
	}

	public ProductSpecification setMaxPrice(final int limit)
	{
		maxPrice = limit;
		return this;
	}

	public boolean isProductSpecified()
	{
		return !productName.isEmpty();
	}

	public boolean isMinPriceSpecified()
	{
		return minPrice >= 0;
	}

	@Override
	public String toString()
	{
		return "ProductSpecification{" +
				"store='" + store + '\'' +
				", productName='" + productName + '\'' +
				", category='" + category + '\'' +
				", minPrice=" + minPrice +
				", maxPrice=" + maxPrice +
				'}';
	}
}
