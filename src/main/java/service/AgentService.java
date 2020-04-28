package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.CustomerDao;
import datasource.HikariCPDataSource;
import entity.Customer;
import entity.CustomerQuery;

public class AgentService {

	public CustomerQuery getCustomers(Integer page, Integer pageSize, String name, String address, String phone)
			throws ClassNotFoundException, SQLException {
		Connection connection = null;
		try {
			connection = HikariCPDataSource.getConnection();
			CustomerDao customerDao = new CustomerDao(connection);
			CustomerQuery customerQuery = new CustomerQuery();
			List<Customer> customers = customerDao.get(page, pageSize, name, address, phone);
			customerQuery.setCustomers(customers);
			Long customersCount = customerDao.getCount(name, address, phone);
			customerQuery.setCustomersCount(customersCount);
			return customerQuery;
		} catch (SQLException e) {
			throw e;
		} finally {
			connection.close();
		}
	}
}
