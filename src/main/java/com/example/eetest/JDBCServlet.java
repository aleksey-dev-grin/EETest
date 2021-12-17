package com.example.eetest;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.*;

public class JDBCServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver's fuck up");
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employees_db", "alekseygrin", "");
            Statement stmn = con.createStatement();
            ResultSet rs = stmn.executeQuery("SELECT department, SUM(salary) AS sum FROM employees GROUP BY department ORDER BY sum");

            while(rs.next()) {
                pw.println(rs.getString("department") + ": " + rs.getString("sum"));
            }
            
            stmn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
