package team.kfc.shopping;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
@Service
public class Catalog
{
	private static final Logger LOG = LoggerFactory.getLogger(Catalog.class);
	private static final String DEFAULT_GATEWAY_URL = "ec-occ-commerce-webservices-v2-47235-lawful-resource";

	private final RestTemplate client = new RestTemplate();

	public String findProducts(final ProductSpecification spec)
	{
		return findProducts(spec.getStore(), asQuery(spec));
	}

	public String findProducts(final String store, final String query)
	{
		final String url = productSearch(store, query);
		LOG.info("Querying {}", url);
		return performCatalogSearch(url)
				.map(this::extractSearchResult)
				.orElse("");
	}

	private Optional<ResponseEntity<String>> performCatalogSearch(final String url)
	{
		try
		{
			return Optional.of(client.getForEntity(url, String.class));
		}
		catch (final RuntimeException e)
		{
			LOG.error("Failed to query the OCC service", e);
			return Optional.empty();
		}
	}

	private String extractSearchResult(final ResponseEntity<String> response)
	{
		final HttpStatus statusCode = response.getStatusCode();
		LOG.info("Response: {} / Headers: {}", statusCode, response.getHeaders());
		if (statusCode == HttpStatus.OK)
		{
			return response.getBody();
		}
		LOG.warn("Response status is {} for product search", statusCode);
		return "";
	}

	private String productSearch(final String store, final String query)
	{
		final String gatewayUrl = System.getenv("GATEWAY_URL");
		return (gatewayUrl == null || gatewayUrl.isEmpty() ? DEFAULT_GATEWAY_URL : gatewayUrl) + "/" + store + "?query=" + query;
	}

	private String asQuery(final ProductSpecification spec)
	{
		final StringBuilder builder = new StringBuilder("freeTextSearch:sort:");
		if (spec.isProductSpecified())
		{
			builder.append("name:").append(spec.getProductName());
		}
		if (spec.isMinPriceSpecified())
		{
			builder.append("minQuantity:").append(spec.getMinPrice());
		}
		return builder.toString();
	}
}
