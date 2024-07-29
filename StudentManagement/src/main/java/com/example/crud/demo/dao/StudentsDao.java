package com.example.crud.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.crud.demo.model.Students;

public class StudentsDao {

	    private String jdbcURL = "jdbc:mysql://localhost:3307/demo?allowPublicKeyRetrieval=true&useSSL=false";
	    private String jdbcStudentname = "root";
	    private String jdbcPassword = "admin";

	    private static final String INSERT_STUDENTS_SQL = "INSERT INTO Students" + "  (name, email, country) VALUES " +
	        " (?, ?, ?);";

	    private static final String SELECT_STUDENTS_BY_ID = "select id,name,email,country from Students where id =?";
	    private static final String SELECT_ALL_STUDENTS = "select * from Students";
	    private static final String DELETE_STUDENTS_SQL = "delete from Students where id = ?;";
	    private static final String UPDATE_STUDENTS_SQL = "update Students set name = ?,email= ?, country =? where id = ?;";

	    public StudentsDao() {}

	    protected Connection getConnection() {
	        Connection connection = null;
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection(jdbcURL, jdbcStudentname, jdbcPassword);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return connection;
	    }

	    public void insertStudent(Students Student) throws SQLException {
	        System.out.println(INSERT_STUDENTS_SQL);
	        // try-with-resource statement will auto close the connection.
	        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENTS_SQL)) {
	            preparedStatement.setString(1, Student.getName());
	            preparedStatement.setString(2, Student.getEmail());
	            preparedStatement.setString(3, Student.getCountry());
	            System.out.println(preparedStatement);
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    }

	    public Students selectStudent(int id) {
	        Students student = null;
	        // Step 1: Establishing a Connection
	        try (Connection connection = getConnection();
	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENTS_BY_ID);) {
	            preparedStatement.setInt(1, id);
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String country = rs.getString("country");
	                student = new Students(id, name, email, country);
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return student;
	    }

	    public List <Students> selectAllStudents() {

	        // using try-with-resources to avoid closing resources (boiler plate code)
	        List <Students> students = new ArrayList < > ();
	        // Step 1: Establishing a Connection
	        try (Connection connection = getConnection();

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS);) {
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String country = rs.getString("country");
	                students.add(new Students(id, name, email, country));
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return students;
	    }

	    public boolean deleteStudent(int id) throws SQLException {
	        boolean rowDeleted;
	        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_STUDENTS_SQL);) {
	            statement.setInt(1, id);
	            rowDeleted = statement.executeUpdate() > 0;
	        }
	        return rowDeleted;
	    }

	    public boolean updateStudent(Students student) throws SQLException {
	        boolean rowUpdated;
	        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENTS_SQL);) {
	            statement.setString(1, student.getName());
	            statement.setString(2, student.getEmail());
	            statement.setString(3, student.getCountry());
	            statement.setInt(4, student.getId());

	            rowUpdated = statement.executeUpdate() > 0;
	        }
	        return rowUpdated;
	    }

	    private void printSQLException(SQLException ex) {
	        for (Throwable e: ex) {
	            if (e instanceof SQLException) {
	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                System.err.println("Message: " + e.getMessage());
	                Throwable t = ex.getCause();
	                while (t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
	}