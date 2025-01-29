package com.capstoneproject.fooddeliveryapp.userInfo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserInfoApplication {

	@Autowired 
	private DataSource dataSource;
	
	public static void main(String[] args) {
		SpringApplication.run(UserInfoApplication.class, args);
	}
	
	@PostConstruct
    public void init() {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("Database connection successful: " + conn.getCatalog());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
