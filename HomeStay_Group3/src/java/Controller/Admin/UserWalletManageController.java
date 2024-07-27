/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import DAO.UserWalletDAO;
import Model.User;
import Model.UserWallet;
import Model.ViewModel.UserWalletOrderVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserWalletManageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("USER") != null) {
                User user = (User) session.getAttribute("USER");

                String indexS = request.getParameter("index");
                String searchS = request.getParameter("search");
                if (indexS == null) {
                    indexS = "1";
                }
                if (searchS == null) {
                    searchS = "";
                }
                String statusS = request.getParameter("status");
                if (statusS == null || statusS == "") {
                    // neu truong hop k truyen gi hoac vao mac dinh se lay het request.
                    statusS = "4";
                }
                int index = Integer.parseInt(indexS);
                int status = Integer.parseInt(statusS);
                UserWalletDAO walletDAO = new UserWalletDAO();
                List<UserWalletOrderVM> orders = walletDAO.getWalletOrders(index, status);
                int total = walletDAO.getWalletOrdersTotal(status);
                int lastPage = total / 5;
                if (total % 5 != 0) {
                    lastPage++;
                }
                
                List<UserWallet> listWallet = walletDAO.getUserWallet();
                request.setAttribute("endP", lastPage);           
                request.setAttribute("USER_WALLETS", listWallet);

                request.setAttribute("selectedPage", index);
                request.setAttribute("status", status);
                request.setAttribute("WALLET_ORDERS", orders);
                request.getRequestDispatcher("/views/admin/wallet-manage.jsp").forward(request, response);
            } else {
                response.sendRedirect("auth?action=login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("USER") != null) {
                User user = (User) session.getAttribute("USER");
                String action = request.getParameter("action");
                UserWalletDAO walletDAO = new UserWalletDAO();

                if (action.equals("approve")) {
                    int orderId = Integer.parseInt(request.getParameter("orderId"));
                    boolean result = walletDAO.approveWalletOrder(orderId);
                    if (result) {
                        response.sendRedirect("UserWalletManageController");
                        
                    } else {
                        request.setAttribute("ERROR_MESSAGE", "Failed to approve wallet order.");
                    }
                } else if (action.equals("reject")) {
                    int orderId = Integer.parseInt(request.getParameter("orderId"));
                    boolean result = walletDAO.rejectWalletOrder(orderId);
                    if (result) {
                        response.sendRedirect("UserWalletManageController");
                    } else {
                        request.setAttribute("ERROR_MESSAGE", "Failed to reject wallet order.");
                    }
                } 
            } else {
                response.sendRedirect("auth?action=login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("auth?action=login");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
