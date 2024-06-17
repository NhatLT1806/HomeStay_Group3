/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.CreateModel.UserSignUp;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static java.time.LocalDateTime.now;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import ultis.EncryptString;

public class AuthenticationDAO extends DBContext {

    private Connection con;
    private List<User> user;
    PreparedStatement ps;
    ResultSet rs;

    public AuthenticationDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User Login(String username, String password) {
        String sql = "SELECT * FROM [User] WHERE [UserName] = ? ";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                int userId = rs.getInt("UserId");
                String userName = rs.getString("UserName");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                boolean isActive = rs.getBoolean("IsActive");
                boolean IsConfirm = rs.getBoolean("IsConfirmEmail");
                int roleId = rs.getInt("RoleId");
                var passowrd = rs.getString("Password");
                byte[] imgData = rs.getBytes("Avatar");
                String base64Image = null;
                if (imgData != null) {
                    base64Image = Base64.getEncoder().encodeToString(imgData);
                }

                user.setId(userId);
                user.setUserName(userName);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhone(phone);
                user.setEmail(email);
                user.setIsActive(isActive);
                user.setIsCofirm(IsConfirm);
                user.setAvatar(base64Image);
                user.setRoleId(roleId);
                String passwordHashed = EncryptString.hashPassword(password);
                if (passowrd.equals(passwordHashed)) {
                    return user;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot found");
        }
        return user;
    }

    public int Register(UserSignUp userSignUp) {

        try {
            boolean isDuplicateUserName = CheckUserName(userSignUp.getUserName());
            if (isDuplicateUserName) {
                return 2;
            }

            boolean isDuplicateEmail = CheckEmail(userSignUp.getEmail());
            if (isDuplicateEmail) {
                return 3;
            }
            String sql = "INSERT INTO [User] (UserName, Password, Email, FirstName, LastName, Phone, IsActive, RoleId, IsConfirmEmail, CreateAt)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, userSignUp.getUserName());
            ps.setString(2, userSignUp.getPassword());
            ps.setString(3, userSignUp.getEmail());
            ps.setString(4, userSignUp.getFirstName());
            ps.setString(5, userSignUp.getLastName());
            ps.setString(6, userSignUp.getPhone());
            ps.setBoolean(7, true);
            // default role is 1 is user
            ps.setInt(8, 1);
            ps.setBoolean(9, false);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setString(10, currentDate);

            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Register Cannot Register");
        }
        return 0;
    }

    public String GetUserRole(int RoleId) {
        String sql = "SELECT * FROM [Role] WHERE [RoleId] = ? ";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, RoleId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("RoleName");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GetUserRole Cannot found");
        }
        return null;
    }

    public boolean CheckUserName(String userName) {
        String sql = "SELECT * FROM [User] WHERE [UserName] = ? ";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CheckUserName Cannot find User");
        }
        return false;
    }

    public boolean CheckEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE [Email] = ? ";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CheckUserName Cannot find User");
        }
        return false;
    }

    public boolean ConfirmEmail(String userName) {
        String sql = "UPDATE [User] SET IsConfirmEmail = ? WHERE UserName = ?";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setString(2, userName);

            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return true;
            } else {
                System.out.println("Confirm email Cannot found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ConfirmEmail Cannot found");
        }
        return false;
    }

    public boolean ForgotPassWord(String password, String email) {
        String sql = "UPDATE [User] SET Password = ? WHERE Email = ?";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, email);

            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return true;
            } else {
                System.out.println("ForgotPassWord Cannot found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ForgotPassWord Cannot found");
        }
        return false;
    }
    public static void main(String[] args) {
        AuthenticationDAO authDAO = new AuthenticationDAO();
        User user = authDAO.Login("datnt123", "Dat123!!");
        System.out.println(user);
    }
}
