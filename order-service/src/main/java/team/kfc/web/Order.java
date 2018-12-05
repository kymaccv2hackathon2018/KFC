package team.kfc.web;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 */
@Entity
@JsonSerialize
@Table(name = "OrderEvents")
public class Order
{
	@Id
	@Column(name = "orderId")
	private String id;
	private String status;


	public String getId()
	{
		return id;
	}

	public void setId(final String id)
	{
		this.id = id;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(final String status)
	{
		this.status = status;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Order order = (Order) o;
		return id.equals(order.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "Order{" +
				"id='" + id + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
