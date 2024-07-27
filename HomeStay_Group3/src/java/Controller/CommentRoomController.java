/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.HomestayDAO;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class CommentRoomController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("USER") != null) {
                User userLogin = (User) session.getAttribute("USER");
                String content = request.getParameter("feedback");
                String postIdS = request.getParameter("homestayId");
                int postId = Integer.parseInt(postIdS);
                HomestayDAO homestayDAO = new HomestayDAO();
                boolean result = homestayDAO.commentPost(postId, content, userLogin.getId());
                if (result) {
                    response.sendRedirect("homestay?action=homestay-detail&homestayId=" + postIdS);
                    return;
                }
                request.setAttribute("Failed", "Your comment post failed");
                request.getRequestDispatcher("auth").forward(request, response);
            } else {
                response.sendRedirect("auth?action=login");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
