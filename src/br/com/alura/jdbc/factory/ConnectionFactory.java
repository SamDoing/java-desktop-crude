package br.com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

	//put your mySQL credentials
	//jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC
	private static String dbURL = "jdbc:mysql://localhost/loja?useTimezone=true&serverTimezone=UTC";
	private static String user  = "root";
	private static String pass  = "mysql123";
	
	public DataSource dataSource;

	public ConnectionFactory() {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		
		if(ConnectionFactory.dbURL.isEmpty() || ConnectionFactory.user.isEmpty() || ConnectionFactory.pass.isEmpty())
			throw new RuntimeException("mySQL credentials are empty!");
		
		comboPooledDataSource.setJdbcUrl(ConnectionFactory.dbURL);
		comboPooledDataSource.setUser(ConnectionFactory.user);
		comboPooledDataSource.setPassword(ConnectionFactory.pass);

		this.dataSource = comboPooledDataSource;
	}

	public Connection recuperarConexao() {
		try 
		{
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
