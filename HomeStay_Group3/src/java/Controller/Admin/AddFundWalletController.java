/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import DAO.AuthenticationDAO;
import DAO.UserWalletDAO;
import Model.User;
import Service.MailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddFundWalletController", urlPatterns = {"/admin/AddFundWalletController"})
public class AddFundWalletController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("USER") != null) {
                String userIds = request.getParameter("userId");
                String amounts = request.getParameter("amount");
                Double amount = Double.parseDouble(amounts);
                if (amount < 0 || amount > 10000000) {
                    request.setAttribute("ERROR", "Money add fund is not validate, you can just type positive number and lower than 10.000.000vnd");
                } else {
                    int _userId = Integer.parseInt(userIds);
                    AuthenticationDAO authDAO = new AuthenticationDAO();
                    User user = authDAO.getUserById(_userId);
                    UserWalletDAO userWalletDAO = new UserWalletDAO();
                    boolean result = userWalletDAO.addFundToUserWallet(_userId, amount);
                    request.setAttribute("MESSAGE", "Add money to user walllet susscessfully");
                    
                     String contentMail = "You are added " + amounts + " by admin to your wallet! Please check your wallet!";
                     MailService.sendMailWithInfo(user.getEmail(), contentMail);
                }
                request.getRequestDispatcher("UserWalletManageController").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/auth?action=login");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
