package team.kfc.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import team.kfc.shopping.Catalog;
import team.kfc.shopping.ProductSpecification;

/**
 *
 */
@RestController
public class CatalogController
{
	private final Catalog catalog;

	public CatalogController(final Catalog c)
	{
		catalog = c;
	}

	@GetMapping("/catalog/products")
	public ResponseEntity<String> searchProductsBySpec(
			@RequestParam(defaultValue = "electronics") final String store,
			@RequestParam(defaultValue = "") final String product,
			@RequestParam(defaultValue = "") final String category,
			@RequestParam(defaultValue = "-1") final int minPrice,
			@RequestParam(defaultValue = "-1") final int maxPrice)
	{
		final ProductSpecification spec = new ProductSpecification()
				.setStore(store)
				.setProductName(product)
				.setCategory(category)
				.setMinPrice(minPrice)
				.setMaxPrice(maxPrice);
		final String entity = catalog.findProducts(spec);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@GetMapping("/catalog/search")
	public ResponseEntity<String> searchProductsByQuery(
			@RequestParam(defaultValue = "electronics") final String store,
			@RequestParam final String query)
	{
		final String entity = catalog.findProducts(store, query);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
}
