/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package team.kfc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import team.kfc.orders.OrderNumber;
import team.kfc.repo.OrderRepository;

@RestController
public class OrderServiceController
{
	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceController.class);

	private final OrderRepository repository;

	public OrderServiceController(final OrderRepository repo)
	{
		repository = repo;
	}

	@GetMapping(value = "/orders/{id}/status", produces = {"application/json"})
	public ResponseEntity<Order> getStatus(@PathVariable final String id)
	{
		LOG.info("Order {} status requested", id);
		final Order order = repository.findOrderById(id);
		return order != null ?
				new ResponseEntity<>(order, HttpStatus.ACCEPTED) :
				new ResponseEntity<>(new NotFoundOrder(id), HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/orders",
			consumes = {"application/json", "application/xml"},
			produces = {"application/json", "application/xml"})
	public ResponseEntity<Order> saveOrder(@RequestBody final Order order)
	{
		final String orderNumber = new OrderNumber(order.getId()).asString(); // removes 0-padding
		order.setId(orderNumber);
		return new ResponseEntity<>(repository.save(order), HttpStatus.CREATED);
	}
}
