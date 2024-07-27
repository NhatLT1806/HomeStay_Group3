/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.Homestay;
import Model.User;
import Model.UserWallet;
import Model.ViewModel.CommentViewModel;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;


public class HomestayDAO {

    private Connection con;
    private List<User> user;
    PreparedStatement ps;
    ResultSet rs;

    public HomestayDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean PostHomestay(Homestay homestay, Part homeStayImage) {
        try {
            String sql = "INSERT INTO [Homestay] (Name, Image, Address, Status, UserId, Description, CreateAt)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, homestay.getName());

            InputStream fileContent = homeStayImage.getInputStream();
            ps.setBinaryStream(2, fileContent, (int) homeStayImage.getSize());

            ps.setString(3, homestay.getAddress());
            ps.setInt(4, homestay.getStatus());
            ps.setInt(5, homestay.getUserId());
            ps.setString(6, homestay.getDescription());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setString(7, currentDate);
            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot Post HomeStay");
        }
        return false;
    }

    public int getTotalHomePage() {
        try {
            String sql = "Select COUNT(*) FROM Homestay";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Homestay> getAll_HomePage(int index) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            // status = 0;
            // 
            String sql = "SELECT * FROM Homestay WHERE Status = 1 ORDER BY CreateAt DESC OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, (index - 1) * 12);
            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getAll_HomePageSearchTotal(String search) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            // status = 0;
            // 
            String sql = "SELECT COUNT(*) FROM Homestay WHERE Status = 1 AND Name LIKE ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Homestay> getAll_HomePageSearch(int index, String search) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            // status = 0;
            // 
            String sql = "SELECT * FROM Homestay WHERE Status = 1 AND Name LIKE ? ORDER BY CreateAt DESC OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, (index - 1) * 12);

            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Homestay> GetListHomeStayByUserId(int userId, int status, int index) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();

            if (status != 7) {
                String sql = "SELECT * FROM Homestay WHERE UserId = ? AND Status = ? ORDER BY HomestayId DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
                ps = con.prepareStatement(sql);
                ps.setInt(1, userId);
                ps.setInt(2, status);
                ps.setInt(3, (index - 1) * 6);
            } else {
                String sql = "SELECT * FROM Homestay WHERE UserId = ? ORDER BY HomestayId DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
                ps = con.prepareStatement(sql);
                ps.setInt(1, userId);
                ps.setInt(2, (index - 1) * 6);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public int GetListHomeStayByUserIdTotal(int userId, int status) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            if (status != 7) {
                String sql = "SELECT COUNT(*) FROM Homestay WHERE UserId = ? AND Status = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, userId);
                ps.setInt(2, status);
            } else {
                String sql = "SELECT COUNT(*) FROM Homestay WHERE UserId = ? ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, userId);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Homestay> GetListHomeStayByUserId_All(int userId) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "SELECT * FROM Homestay WHERE UserId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Homestay GetHomestayById(int homestayId) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "SELECT * FROM Homestay WHERE HomestayId = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                return homestay;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User GetOnwerByHomestayId(int homestayId) {
        try {
            String sql = "SELECT * FROM Homestay h JOIN [USER] u ON h.UserId = u.UserId WHERE HomestayId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("UserId"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                byte[] imgData = rs.getBytes("Avatar");
                String base64Image = null;
                if (imgData != null) {
                    base64Image = Base64.getEncoder().encodeToString(imgData);
                }
                user.setAvatar(base64Image);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User GetCustomerByRequestId(int requestId) {
        try {
            String sql = "SELECT * FROM Booking_Request WHERE RequestId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, requestId);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("BookBy"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Homestay> getHomeStayWaitToConfirm(int index) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "SELECT h.*, u.FirstName, u.LastName, u.Email, u.Phone FROM Homestay h JOIN [User] u ON h.UserId = u.UserId"
                    + " WHERE Status = 0 ORDER BY CreateAt DESC OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, (index - 1) * 12);
            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                User user = new User();
                user.setEmail(rs.getString("Email"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setPhone(rs.getString("Phone"));

                homestay.setUser(user);
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setUserId(rs.getInt("UserId"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean AcceptHomeStay(int homestayId, int ownerId) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "UPDATE Homestay SET Status = 1 WHERE HomestayId = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                UserWalletDAO userWalletDAO = new UserWalletDAO();
                UserWallet userWallet = userWalletDAO.getUserWalletById(ownerId);
                float ammount = userWallet.getAmmount() * 10000;
                ammount = ammount - 10000;
                sql = "UPDATE UserWallet SET Ammount = ? WHERE Id = ?";
                ps = con.prepareStatement(sql);
                ps.setFloat(1, ammount);
                ps.setInt(2, userWallet.getId());
                affectedRow = ps.executeUpdate();
                String content = "Tạo nhà id - " + homestayId + " thành công";
                boolean result = userWalletDAO.addTransitionHistory(content, userWallet.getId());
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean RejectHomeStay(int homestayId) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "UPDATE Homestay SET Status = 2 WHERE HomestayId = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Comment
    public boolean commentPost(int postId, String Content, int UserId) {
        try {
            String sql = "INSERT INTO [Comment] (HomestayId, Content, UserId, CreateAt)"
                    + " VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setString(2, Content);
            ps.setInt(3, UserId);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setString(4, currentDate);
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // get lisst comment
    public int getListUserCommentInPostTotal(int postId) {
        try {
            String sql = "SELECT COUNT(*) FROM [Comment] WHERE HomestayId = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // get lisst comment
    public List<CommentViewModel> getListUserCommentInPost(int postId, int index) {
        try {
            int userId = 0;
            List<CommentViewModel> listComment = new ArrayList<>();
            String sql = "SELECT * FROM [Comment] WHERE HomestayId = ? ORDER BY Id DESC OFFSET ? ROW FETCH NEXT 12 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setInt(2, (index - 1) * 12);
            rs = ps.executeQuery();
            while (rs.next()) {
                CommentViewModel commentVM = new CommentViewModel();
                commentVM.setId(rs.getInt("Id"));
                commentVM.setPostId(rs.getInt("HomestayId"));
                commentVM.setContent(rs.getString("Content"));
                commentVM.setUserId(rs.getInt("UserId"));
                commentVM.setCreateAt(rs.getString("CreateAt"));
                // luu cai userId cua nguoi comment
                userId = rs.getInt("UserId");
                sql = "SELECT * FROM [User] WHERE UserId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, userId);
                ResultSet rs1 = ps.executeQuery();
                while (rs1.next()) {
                    String firstName = rs1.getString("FirstName");
                    String lastName = rs1.getString("LastName");
                    String fullName = firstName + " " + lastName;
                    commentVM.setUserName(fullName);
                    String base64Image = null;
                    byte[] imgData = rs1.getBytes("Avatar");
                    if (imgData != null) {
                        base64Image = Base64.getEncoder().encodeToString(imgData);
                    }
                    commentVM.setAvatar(base64Image);
                }
                listComment.add(commentVM);
            }
            return listComment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getHomeStayByRoomId(int roomId) {
        try {
            String sql = "SELECT * FROM [Room] WHERE RoomId = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("HomestayId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
     public int getUserIdByHomeStayId(int homestayId) {
        try {
            String sql = "SELECT * FROM [Homestay] WHERE HomestayId = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("UserId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    

    public int getAll_HomePageSearchAddressTotal(String search) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>(); // status = 0;    
            String sql = "SELECT COUNT(*) FROM Homestay WHERE Status = 1 AND Address LIKE ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Homestay> getAll_HomePageSearchAddress(String search, int index) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "SELECT * FROM Homestay WHERE Status = 1 AND Address LIKE ? ORDER BY CreateAt DESC OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, (index - 1) * 12);

            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setUserId(rs.getInt("UserId"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void updateHomeStayStatus(int homestayId) {
        try {
            Homestay h = GetHomestayById(homestayId);
            if(h != null) {
                int status = h.getStatus();
                if(status == 1) {
                    status = 4;
                } else { 
                    status = 1;
                }
                
                String sql = "UPDATE Homestay Set Status = ? WHERE HomestayId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, status);            
                ps.setInt(2, homestayId);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Homestay updateHomeStay(Homestay homestay) {
        try {
            String sql = "UPDATE Homestay SET Name = ?, Address = ?, Description = ? WHERE HomestayId = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, homestay.getName());          
            ps.setString(2, homestay.getAddress());
            ps.setString(3, homestay.getDescription());
            ps.setInt(4, homestay.getHomestayId());
            int affectedRow = ps.executeUpdate();
            if(affectedRow > 0) {
                Homestay updateHomeStay = GetHomestayById(homestay.getHomestayId());
                return updateHomeStay;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        HomestayDAO dao = new HomestayDAO();
        Homestay home = new Homestay();
        home.setHomestayId(1);
        home.setName("Test Update");       
        home.setDescription("Test Update");     
        home.setAddress("Test Update");

        int ownerId = dao.getUserIdByHomeStayId(11);
        System.out.println(ownerId);
    }
}
