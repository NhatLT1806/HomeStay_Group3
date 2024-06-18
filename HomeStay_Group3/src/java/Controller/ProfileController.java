/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AuthenticationDAO;
import DAO.ProfileDAO;
import Model.User;
import Service.MailService;
import Service.OtpService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import ultis.EncryptString;


@MultipartConfig
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String url = "";
            HttpSession session = request.getSession(false);
            // Get session ra neu khong co session account tuc la chua login thi response ve trang login.
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            if (session != null && session.getAttribute("USER") != null) {
                User user = (User) session.getAttribute("USER");
                switch (action) {
                    case "view": {
                        // set thong tin cua user vao bien requestScope user
                        request.setAttribute("USER", user);
                        url = "views/user/profile.jsp";
                        break;
                    }
                    case "changePassword": {
                        url = "views/user/change-password.jsp";
                        break;
                    }
                    case "forgotPassword":
                        forgotPassword(request, response);
                        url = "views/user/send-mail-noti.jsp";
                        break;

                }
            } else {
                // trang login
                url = "views/common/sign-in.jsp";
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        if (session != null && session.getAttribute("USER") != null) {
            switch (action) {
                case "updateProfile":
                    updateProfile(request, response);
                    break;
                case "changePassword":
                    changePassword(request, response);
                    break;
//                case "setNewPassword":
//                    setNewPassword(request, response);
//                    break;
            }
        } else {
            response.sendRedirect("views/common/sign-in.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void updateProfile(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            Part image = request.getPart("image");
            User userLogin = (User) session.getAttribute("USER");
            ProfileDAO profileDAO = new ProfileDAO();
            User user = new User();
            user.setId(userLogin.getId());
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);

            User userUpdate = profileDAO.updateProfile(user, image);

            if (userUpdate != null) {
                session.setAttribute("USER", userUpdate);
                request.setAttribute("MESSAGE", "Cập nhật hồ sơ thành công");
            } else {
                request.setAttribute("ERRORMESSAGE", "Cập nhật hồ sơ không thành công");
            }
            request.getRequestDispatcher("views/user/profile.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("UpdateProfile Cannot update");
            e.printStackTrace();
        }

    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = "views/user/profile.jsp";
            HttpSession session = request.getSession(false);
            String oldPassword = request.getParameter("oldpassword");
            String newPassword = request.getParameter("newPassword");
            User userLogin = (User) session.getAttribute("USER");
            ProfileDAO profileDAO = new ProfileDAO();
            User user = new User();
            user.setId(userLogin.getId());
            user.setPassword(oldPassword);
            user.setUserName(userLogin.getUserName());

            boolean result = profileDAO.changePassword(user, newPassword);
            if (result) {
                request.setAttribute("MESSAGE", "Cập nhật mật khẩu thành công");
            } else {
                request.setAttribute("ERRORMESSAGE", "Cập nhật mật khẩu không thành công");
                url = "views/user/change-password.jsp";
            }
            System.out.println("Change password " + result);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            System.out.println("UpdateProfile Cannot update");
            e.printStackTrace();
        }
    }

    private void forgotPassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("USER");
            // gọi otp Service
            OtpService optService = new OtpService();
            String otp = OtpService.genarateOtp();

            session.setAttribute("otp", otp);
            String email = userLogin.getEmail().trim();
            MailService mailService = new MailService();
            mailService.sendOtpToMail(email, otp);
        } catch (Exception e) {
            System.out.println("ForgotPassword Get cannot found");
            e.printStackTrace();
        }

    }

    private void setNewPassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = "views/user/confirm-success.jsp";
            HttpSession session = request.getSession();
            String newPassword = request.getParameter("newPassword");

            String passwordHashed = EncryptString.hashPassword(newPassword);
            User userLogin = (User) session.getAttribute("USER");
            String email = userLogin.getEmail().trim();
            AuthenticationDAO authDAO = new AuthenticationDAO();
            boolean result = authDAO.ForgotPassWord(passwordHashed, email);
            if (result) {
                url = "views/common/sign-in.jsp";
                request.setAttribute("MESSAGE", "Reset password thành công hãy đăng nhập!");
            } else {
                request.setAttribute("ERRROR", "ERROR in reset password");
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmOTP(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
