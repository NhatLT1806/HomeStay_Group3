/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
    
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DashboardDAO extends DBContext {

    private Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    
    public DashboardDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public Map<YearMonth, Integer> getMonthlyConfirmedRequest() {
        Map<YearMonth, Integer> monthlyConfirmedPosts = new HashMap<>();
        try {
            String sql = "SELECT YEAR(CreateAt) AS Year, MONTH(CreateAt) AS Month, COUNT(*) AS TotalConfirmedPosts " 
                    + "FROM Booking_Request " + "WHERE Status = 1 " 
                    + "GROUP BY YEAR(CreateAt), MONTH(CreateAt)";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int year = rs.getInt("Year");
                int month = rs.getInt("Month");
                int totalConfirmedPosts = rs.getInt("TotalConfirmedPosts");
                YearMonth yearMonth = YearMonth.of(year, month);
                monthlyConfirmedPosts.put(yearMonth, totalConfirmedPosts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return monthlyConfirmedPosts;
    }

    public Map<YearMonth, Integer> getMonthlyCreateBookingRequest() {
        Map<YearMonth, Integer> monthlySavedPosts = new HashMap<>();
        try {
            String sql = "SELECT YEAR(CreateAt) AS Year, MONTH(CreateAt) AS Month, COUNT(*) AS TotalSavedPosts " 
                    + "FROM Booking_Request " + "WHERE Status = 0 " 
                    + "GROUP BY YEAR(CreateAt), MONTH(CreateAt)";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int year = rs.getInt("Year");
                int month = rs.getInt("Month");
                int totalSavedPosts = rs.getInt("TotalSavedPosts");
                YearMonth yearMonth = YearMonth.of(year, month);
                monthlySavedPosts.put(yearMonth, totalSavedPosts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return monthlySavedPosts;
    }
}
