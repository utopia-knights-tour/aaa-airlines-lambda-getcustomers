package entity;

import java.util.List;

public class CustomerQuery {
	private List<Customer> customers;
	private Long customersCount;

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Long getCustomersCount() {
		return customersCount;
	}

	public void setCustomersCount(Long customersCount) {
		this.customersCount = customersCount;
	}
}
