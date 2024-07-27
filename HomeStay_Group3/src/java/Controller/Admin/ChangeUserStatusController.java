/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import DAO.UserManageDAO;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;



public class ChangeUserStatusController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("USER") != null) {
                String userIds = request.getParameter("userId");
                int userId = Integer.parseInt(userIds);
                UserManageDAO userManageDAO = new UserManageDAO();
                boolean result = userManageDAO.changeStatus(userId);
                String indexS = request.getParameter("index");
                if (indexS == null) {
                    indexS = "1";
                }
                int index = Integer.parseInt(indexS);
                int total = userManageDAO.GetAllUserTotal();
                List<User> userList = userManageDAO.GetAllUser(index);
                int lastPage = total / 10;
                if (total % 10 != 0) {
                    lastPage++;
                }
                request.setAttribute("LIST_USER", userList);
                request.setAttribute("endP", lastPage);
                request.setAttribute("selectedPage", index);
            }
            request.getRequestDispatcher( "/views/admin/manage-user.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
