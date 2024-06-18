/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AuthenticationDAO;
import Model.User;
import Service.MailService;
import Service.OtpService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


public class VerifyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String url = "views/common/forgot-password.jsp";
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "check": {
              ValidateOtp(request, response);
              break;
            }
            case "sendOtpToMail": {
                sendMail(request, response);
                break;
            }
            case "setNewPassword": {
                setNewPassword(request, response);
                break;
            }
        }
    }

    private void ValidateOtp(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Lấy OTP từ session
            HttpSession session = request.getSession();
            String otp = (String) session.getAttribute("otp");
            User UserLogin = (User) session.getAttribute("USER");
            String url = "views/user/confirm-success.jsp";

            // Lấy OTP nhập từ người dùng
            String enteredOTP = request.getParameter("otp");

            // Kiểm tra xem OTP nhập có đúng không
            if (otp != null && !otp.isEmpty() && otp.equals(enteredOTP)) {
                // Xác minh OTP thành công
                String successMessage = "OTP verified successfully!";
                request.setAttribute("MESSAGE", successMessage);
                System.out.println("Successfully roi");
            } else {
                // OTP không đúng
                String errorMessage = "OTP verification failed!";
                request.setAttribute("ERROR", errorMessage);
                url = "views/user/send-mail-noti.jsp";
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNewPassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = "views/user/confirm-success.jsp";
            HttpSession session = request.getSession();
            String newPassword = request.getParameter("newPassword");
            String email = (String) session.getAttribute("EMAIL");
            AuthenticationDAO authDAO = new AuthenticationDAO();
            boolean result = authDAO.ForgotPassWord(newPassword, email.trim());
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

    private void sendMail(HttpServletRequest request, HttpServletResponse response) {
        try {
            String email = request.getParameter("email");
            HttpSession session = request.getSession();
            OtpService optService = new OtpService();
            String otp = OtpService.genarateOtp();
            session.setAttribute("otp", otp);
            session.setAttribute("EMAIL", email.trim());
            MailService mailService = new MailService();
            mailService.sendOtpToMail(email, otp);
            request.getRequestDispatcher("views/user/send-mail-noti.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
