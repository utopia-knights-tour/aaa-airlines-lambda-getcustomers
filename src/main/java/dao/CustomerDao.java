package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Customer;

public class CustomerDao {

	private Connection connection;

	public CustomerDao(Connection connection) {
		this.connection = connection;
	}

	public List<Customer> get(Integer page, Integer pageSize, String name, String address, String phone)
			throws SQLException {
		boolean first = true;
		List<Customer> customers = new ArrayList<Customer>();
		String sql = "SELECT * FROM Customer";
		if (name != null) {
			if (first) {
				sql += " WHERE Customer.customerName LIKE ?";
				first = false;
			} else {
				sql += " AND Customer.customerName LIKE ?";
			}
		}
		if (address != null) {
			if (first) {
				sql += " WHERE Customer.customerAddress LIKE ?";
				first = false;
			} else {
				sql += " AND Customer.customerAddress LIKE ?";
			}
		}
		if (phone != null) {
			if (first) {
				sql += " WHERE Customer.customerPhone LIKE ?";
				first = false;
			} else {
				sql += " AND Customer.customerPhone LIKE ?";
			}
		}
		if (page != null && pageSize != null) {
			sql += " LIMIT ?,?";
		}
		PreparedStatement pstmt = connection.prepareStatement(sql);
		int parameterIndex = 1;
		if (name != null) {
			pstmt.setString(parameterIndex, name + "%");
			parameterIndex++;
		}
		if (address != null) {
			pstmt.setString(parameterIndex, address + "%");
			parameterIndex++;
		}
		if (phone != null) {
			pstmt.setString(parameterIndex, phone + "%");
			parameterIndex++;
		}
		if (page != null && pageSize != null) {
			pstmt.setInt(parameterIndex, (page - 1) * 10);
			parameterIndex++;
			pstmt.setInt(parameterIndex, pageSize);
		}
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Customer customer = new Customer();
			customer.setId(rs.getLong("customerId"));
			customer.setName(rs.getString("customerName"));
			customer.setAddress(rs.getString("customerAddress"));
			customer.setPhone(rs.getString("customerPhone"));
			customers.add(customer);
		}
		return customers;
	}

	public Long getCount(String name, String address, String phone) throws SQLException {
		boolean first = true;
		String sql = "SELECT COUNT(*) AS count FROM Customer";
		if (name != null) {
			if (first) {
				sql += " WHERE Customer.customerName LIKE ?";
				first = false;
			} else {
				sql += " AND Customer.customerName LIKE ?";
			}
		}
		if (address != null) {
			if (first) {
				sql += " WHERE Customer.customerAddress LIKE ?";
				first = false;
			} else {
				sql += " AND Customer.customerAddress LIKE ?";
			}
		}
		if (phone != null) {
			if (first) {
				sql += " WHERE Customer.customerPhone LIKE ?";
				first = false;
			} else {
				sql += " AND Customer.customerPhone LIKE ?";
			}
		}
		PreparedStatement pstmt = connection.prepareStatement(sql);
		int parameterIndex = 1;
		if (name != null) {
			pstmt.setString(parameterIndex, name + "%");
			parameterIndex++;
		}
		if (address != null) {
			pstmt.setString(parameterIndex, address + "%");
			parameterIndex++;
		}
		if (phone != null) {
			pstmt.setString(parameterIndex, phone + "%");
			parameterIndex++;
		}
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getLong("count");
		}
		return null;
	}
}
