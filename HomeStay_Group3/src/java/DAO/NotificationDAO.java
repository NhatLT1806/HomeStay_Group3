/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.UserNotification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationDAO extends DBContext {

    private Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public NotificationDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNotification(int userId, String title, String content) {
        try {
            String sql = "INSERT INTO Notification (UserId, Title, Content, CreateAt, IsRead) VALUES (?, ?, ?, ?, 0)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setString(3, content);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setString(4, currentDate);
            ps.executeUpdate();
            
            System.out.println("Tạo thành công rồi");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotalNotificationByUserId(int userId) {
        try {
            String sql = "SELECT COUNT(*) FROM Notification WHERE UserId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalNewNotificationByUserId(int userId) {
        try {
            String sql = "SELECT COUNT(*) FROM Notification WHERE UserId = ? AND IsRead = 0";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<UserNotification> getAllNotifyByUserId(int userId, int index) {
        List<UserNotification> notifications = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Notification WHERE UserId = ? ORDER BY IsRead ASC OFFSET ? ROWS FETCH NEXT 10 ROW ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, (index - 1) * 10);

            rs = ps.executeQuery();
            while (rs.next()) {
                UserNotification notification = new UserNotification();
                notification.setId(rs.getInt("Id"));
                notification.setUserId(rs.getInt("UserId"));
                notification.setTitle(rs.getString("Title"));
                notification.setContent(rs.getString("Content"));
                notification.setCreateAt(rs.getString("CreateAt"));
                notification.setIsRead(rs.getBoolean("IsRead"));

                notifications.add(notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public boolean deleteNotification(int notificationId) {
        try {
            String sql = "DELETE FROM Notification WHERE Id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, notificationId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean markIsReadNotify(int notificationId) {
        try {
            String sql = "UPDATE Notification SET IsRead = 1 WHERE Id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, notificationId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        NotificationDAO notiDAO = new NotificationDAO();
        String title = "NẠP TIỀN VÀO VÍ";
        String _content = "Yêu cầu nạp tiền của bạn đã được gửi, vui lòng đợi admin phê duyệt!";
        notiDAO.createNotification(1, title, _content);
    }
}
