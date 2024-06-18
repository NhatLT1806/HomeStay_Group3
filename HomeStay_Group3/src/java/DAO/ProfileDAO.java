/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.User;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import ultis.EncryptString;

/**
 *
 * @author Datnt
 */
public class ProfileDAO extends DBContext {

    private Connection con;
    private List<User> user;
    PreparedStatement ps;
    ResultSet rs;

    public ProfileDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User updateProfile(User user, Part image) {
        try {
            User userUpdate = new User();
            String sql = "";
            if (image != null) {
                sql = "UPDATE dbo.[User] SET [FirstName] = ?, [LastName] = ?, [Phone] = ?, "
                        + "[Email] = ?, UpdateAt = ?, Avatar = ? "
                        + "WHERE [UserId] = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getPhone());
                ps.setString(4, user.getEmail());

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String currentDate = dateFormat.format(date);
                ps.setString(5, currentDate);

                InputStream fileContent = image.getInputStream();
                ps.setBinaryStream(6, fileContent, (int) image.getSize());
                ps.setInt(7, user.getId());
            } else {
                sql = "UPDATE dbo.[User] SET [FirstName] = ?, [LastName] = ?, [Phone] = ?, "
                        + "[Email] = ?, UpdateAt = ? "
                        + "WHERE [UserId] = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getPhone());
                ps.setString(4, user.getEmail());

                LocalDateTime now = LocalDateTime.now();
                ps.setString(5, now.toString());
                ps.setInt(6, user.getId());
            }

            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                sql = "SELECT * FROM dbo.[User] WHERE UserId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, user.getId());
                rs = ps.executeQuery();
                if (rs.next()) {
                    int UserId = rs.getInt("UserId");
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String phone = rs.getString("Phone");
                    String email = rs.getString("Email");
                    byte[] imgData = rs.getBytes("Avatar");
                    String base64Image = null;
                    if (imgData != null) {
                        base64Image = Base64.getEncoder().encodeToString(imgData);
                    }

                    userUpdate.setFirstName(firstName);
                    userUpdate.setLastName(lastName);
                    userUpdate.setPhone(phone);
                    userUpdate.setEmail(email);
                    userUpdate.setId(UserId);
                    userUpdate.setAvatar(base64Image);
                    return userUpdate;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean changePassword(User user, String newPassword) {
        String password = EncryptString.hashPassword(newPassword);     
        String userPassword =   EncryptString.hashPassword(user.getPassword());

        String sql = "SELECT * FROM [User] WHERE [UserName] = ? AND [Password] = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, userPassword);
            rs = ps.executeQuery();
            if (rs.next()) {
                sql = "UPDATE dbo.[User] SET [Password] = ? "
                        + "WHERE [UserName] = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, password);
                ps.setString(2, user.getUserName());
                int affectedRow = ps.executeUpdate();
                if (affectedRow > 0) {
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
