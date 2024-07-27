/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ContractDAO;
import DAO.HomestayDAO;
import Model.Contract;
import Model.Homestay;
import Model.Room;
import Model.User;
import Model.ViewModel.RoomServiceVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Datnt
 */
public class ContractController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("USER") != null) {
                String roomIds = request.getParameter("roomId");      
                String contractIds = request.getParameter("contractId");

                int contractId = Integer.parseInt(contractIds);     
                int roomId = Integer.parseInt(roomIds);

                HomestayDAO homestayDAO = new HomestayDAO();
                int homestayId = homestayDAO.getHomeStayByRoomId(roomId);

                User userLogin = (User) session.getAttribute("USER");

                ContractDAO contractDAO = new ContractDAO();

                Contract contract = contractDAO.getContractByContractId(contractId);
                Room room = contractDAO.getRoomById(roomId);
                List<RoomServiceVM> services = contractDAO.getRoomServices(roomId);
                User owner = contractDAO.getOwnerByHomestayId(homestayId);
                Homestay homestay = homestayDAO.GetHomestayById(homestayId);
                User customer = contractDAO.getUserById(contract.getUserId());

                request.setAttribute("ROOM_SERVICES", services);
                request.setAttribute("CONTRACT", contract);
                request.setAttribute("ROOM", room);
                request.setAttribute("CUSTOMER", customer);
                request.setAttribute("HOMESTAY", homestay);

                request.setAttribute("OWNER", owner);
                request.getRequestDispatcher("views/user/contract.jsp").forward(request, response);
            } else {
                response.sendRedirect("auth?action=login");
            }
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
