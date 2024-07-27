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

public class GetAllUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("USER") != null) {
                String indexS = request.getParameter("index");
                String searchS = request.getParameter("search");
                if (indexS == null) {
                    indexS = "1";
                }
                if (searchS == null) {
                    searchS = "";
                }
                int index = Integer.parseInt(indexS);
                UserManageDAO userManageDAO = new UserManageDAO();
                int total = userManageDAO.GetAllUserTotal();
                List<User> userList = userManageDAO.GetAllUser(index);
                if (searchS != "") {
                    total = userManageDAO.GetAllUserTotalByUserName(searchS);
                    userList = userManageDAO.GetAllUserByUserName(searchS, index);
                    request.setAttribute("search", searchS);
                }
                int lastPage = total / 12;
                if (total % 12 != 0) {
                    lastPage++;
                }
                request.setAttribute("LIST_USER", userList);
                request.setAttribute("endP", lastPage);
                request.setAttribute("selectedPage", index);
            }
            request.getRequestDispatcher("/views/admin/manage-user.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
