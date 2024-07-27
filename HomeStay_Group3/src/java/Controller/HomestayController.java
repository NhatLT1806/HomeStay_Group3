/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CategoryDAO;
import DAO.HomestayDAO;
import DAO.NotificationDAO;
import DAO.RoomDAO;
import DAO.UserWalletDAO;
import Model.Category;
import Model.Contract;
import Model.Homestay;
import Model.Room;
import Model.User;
import Model.UserWallet;
import Model.ViewModel.CommentViewModel;
import Model.ViewModel.RoomServiceVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MultipartConfig
public class HomestayController extends HttpServlet {

    private static String HOME_PAGE = "views/common/index.jsp";
    private static String HOMESTAY_DETAIL = "views/common/homestay-detail.jsp";
    private static String MANAGE_HOMESTAY_OWNER = "views/user/manage-homestay-owner.jsp";

    private static String LOGIN_PAGE = "auth?action=login";
    private static String CREATE_PAGE = "views/user/post-homestay.jsp";
    private static String CUSTOMER_HOMESTAY = "views/user/customer-homestay.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String url = "";
            HttpSession session = request.getSession();
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> listCate = cateDAO.GetAllCategory();
            request.setAttribute("CATEGORIES", listCate);
            boolean isView = true;
            switch (action) {
                case "homestay-detail":
                    homestayDetail(request, response);
                    isView = false;
                    break;
                case "filter-by-price":
                    filterRoomByPrice(request, response);
                    isView = false;
                    break;
                case "filter-by-services": {
                    FilterByServices(request, response);
                    break;
                }

            }
            if (session != null && session.getAttribute("USER") != null) {
                switch (action) {
                    case "post":
                        request.getRequestDispatcher(CREATE_PAGE).forward(request, response);
                        break;
                    case "view-own-homestay":
                        getUserHomeStay(request, response);
                        break;
                    case "view-detail-homestay":
                        viewDetailHomeStay(request, response);
                        break;
                    case "change-status-homestay":
                        changeStatusHomeStay(request, response);
                        break;
                }
            } else {
                if (isView) {
                    response.sendRedirect(LOGIN_PAGE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String url = "";
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            switch (action) {
                case "post":
                    PostHomeStay(request, response);
                    break;
                case "update":
                    UpdateHomestay(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void PostHomeStay(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("USER");
            Homestay homestay = new Homestay();
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String desc = request.getParameter("description");

            Part mainImage = request.getPart("image");

            homestay.setAddress(address);
            homestay.setName(name);
            // default =>? 0 => duoc 1.
            homestay.setStatus(0);
            homestay.setDescription(desc);
            homestay.setUserId(userLogin.getId());

            HomestayDAO homestayDAO = new HomestayDAO();

            UserWalletDAO userWalletDAO = new UserWalletDAO();
            UserWallet userWallet = userWalletDAO.getUserWalletById(userLogin.getId());
            // 10k 1 luot.
            if (userWallet.getAmmount() > 1) {
                boolean result = homestayDAO.PostHomestay(homestay, mainImage);
                if (result) {
                    NotificationDAO notiDAO = new NotificationDAO();
                    String title = "THÊM NHÀ";
                    String _content = "Yêu cầu thêm nhà " + homestay.getName() + " của bạn đã được gửi, vui lòng đợi admin phê duyệt!";
                    notiDAO.createNotification(userLogin.getId(), title, _content);

                    request.setAttribute("MESSAGE", "Tạo homestay thành công ! "
                            + "Vui lòng đợi quản trị viên liên lạc với bạn để xác nhận thông tin,"
                            + " sau đó homestay của bạn sẽ được xuất hiện trên hệ thống của chúng tôi");
                } else {
                    request.setAttribute("ERROR", "Tạo nhà không thành công");
                }
            } else {
                request.setAttribute("ERROR", "Số lượt tạo nhà của bạn không đủ vui lòng nạp thêm");
            }

            request.getRequestDispatcher(CREATE_PAGE).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserHomeStay(HttpServletRequest request, HttpServletResponse response) {
        try {
            String indexS = request.getParameter("index");
            String searchS = request.getParameter("search");
            String statusS = request.getParameter("status");
            if (indexS == null) {
                indexS = "1";
            }
            if (searchS == null) {
                searchS = "";
            }
            if (statusS == null || statusS == "") {
                // neu truong hop k truyen gi hoac vao mac dinh se lay het request.
                statusS = "7";
            }
            int index = Integer.parseInt(indexS);
            int status = Integer.parseInt(statusS);
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("USER");
            HomestayDAO homestayDAO = new HomestayDAO();
            List<Homestay> listHomeStay = homestayDAO.GetListHomeStayByUserId(userLogin.getId(), status, index);
            int total = homestayDAO.GetListHomeStayByUserIdTotal(userLogin.getId(), status);

            int lastPage = total / 6;
            if (total % 6 != 0) {
                lastPage++;
            }
            if (listHomeStay != null) {
                request.setAttribute("HOMESTAY", listHomeStay);
            } else {
                request.setAttribute("MESSAGE", "Bạn chưa có homestay nào trên hệ thống");
            }
            request.setAttribute("status", status);
            request.setAttribute("endP", lastPage);
            request.setAttribute("selectedPage", index);
            request.getRequestDispatcher(CUSTOMER_HOMESTAY).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void homestayDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            String homestayIdS = request.getParameter("homestayId");
            String indexS = request.getParameter("index");
            String searchS = request.getParameter("search");
            if (indexS == null) {
                indexS = "1";
            }
            if (searchS == null) {
                searchS = "";
            }
            int index = Integer.parseInt(indexS);
            int homestayId = Integer.parseInt(homestayIdS);
            RoomDAO roomDAO = new RoomDAO();
            HomestayDAO homestayDAO = new HomestayDAO();
            Homestay homestay = homestayDAO.GetHomestayById(homestayId);
            List<Room> listRoom = roomDAO.GetListRoomByHomeStayId(homestayId, index);
            int total = roomDAO.GetListRoomByHomeStayIdTotal(homestayId);
            if (searchS != "") {
                total = roomDAO.GetListRoomByHomeStayIdSearchTotal(homestayId, searchS);
                listRoom = roomDAO.GetListRoomByHomeStayIdSearch(homestayId, index, searchS);
                request.setAttribute("search", searchS);
            }
            int lastPage = total / 8;
            if (total % 8 != 0) {
                lastPage++;
            }
            if (listRoom != null) {
                request.setAttribute("ROOMS", listRoom);
                request.setAttribute("HOMESTAY", homestay);
            } else {
                request.setAttribute("MESSAGE", "Nhà trọ này hiện tại đã hết phòng");
            }

            String index2S = request.getParameter("index2");
            if (index2S == null) {
                index2S = "1";
            }
            int index2 = Integer.parseInt(index2S);
            int total2 = homestayDAO.getListUserCommentInPostTotal(homestayId);
            List<CommentViewModel> listComment = homestayDAO.getListUserCommentInPost(homestayId, index2);
            int lastPage2 = total2 / 12;
            if (total2 % 12 != 0) {
                lastPage2++;
            }
            request.setAttribute("endP2", lastPage2);
            request.setAttribute("selectedPage2", index2);
            request.setAttribute("COMMENTS", listComment);

            request.setAttribute("endP", lastPage);
            request.setAttribute("selectedPage", index);
            request.setAttribute("homestayId", homestayIdS);

            request.getRequestDispatcher(HOMESTAY_DETAIL).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterRoomByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String homestayIdS = request.getParameter("homestayId");
            String minPriceS = request.getParameter("min-price");
            String maxPriceS = request.getParameter("max-price");
            String indexS = request.getParameter("index");
            int homestayId = Integer.parseInt(homestayIdS);
            float minPrice = minPriceS != null ? Float.parseFloat(minPriceS) : 0;
            float maxPrice = maxPriceS != null ? Float.parseFloat(maxPriceS) : 0;
            int index = indexS != null ? Integer.parseInt(indexS) : 1;
            RoomDAO roomDAO = new RoomDAO();
            int total = roomDAO.GetListRoomByHomeStayIdFilteredTotal(homestayId, minPrice, maxPrice);
            List<Room> filteredRooms = roomDAO.GetListRoomByHomeStayIdFiltered(homestayId, minPrice, maxPrice, index);
            int lastPage = total / 8;
            if (total % 8 != 0) {
                lastPage++;
            }

            String index2S = request.getParameter("index2");
            if (index2S == null) {
                index2S = "1";
            }
            HomestayDAO homestayDAO = new HomestayDAO();
            int index2 = Integer.parseInt(index2S);
            Homestay homestay = homestayDAO.GetHomestayById(homestayId);

            int total2 = homestayDAO.getListUserCommentInPostTotal(homestayId);
            List<CommentViewModel> listComment = homestayDAO.getListUserCommentInPost(homestayId, index2);
            int lastPage2 = total2 / 12;
            if (total2 % 12 != 0) {
                lastPage2++;
            }
            request.setAttribute("HOMESTAY", homestay);
            request.setAttribute("endP2", lastPage2);
            request.setAttribute("selectedPage2", index2);
            request.setAttribute("COMMENTS", listComment);

            request.setAttribute("ROOMS", filteredRooms);
            request.setAttribute("endP", lastPage);
            request.setAttribute("selectedPage", index);
            request.setAttribute("homestayId", homestayIdS);

            request.setAttribute("MIN_PRICE", minPrice);
            request.setAttribute("MAX_PRICE", maxPrice);
            request.getRequestDispatcher(HOMESTAY_DETAIL).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void FilterByServices(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            String homestayIdS = request.getParameter("homestayId");
            String[] selectedServices = request.getParameterValues("selectedServices");
            List<String> services = selectedServices != null ? Arrays.asList(selectedServices) : new ArrayList<>();
            int homestayId = Integer.parseInt(homestayIdS);
            RoomDAO roomDAO = new RoomDAO();
            List<Room> filteredRooms = new ArrayList<>();
            List<Room> allRooms = roomDAO.GetAllListRoomByHomeStayId(homestayId);
            CategoryDAO cateDAO = new CategoryDAO();

            for (Room room : allRooms) {
                List<RoomServiceVM> roomServices = roomDAO.GetRoomService(room.getRoomId());
                boolean matchesServices = true;
                int i = 1;

                for (String service : services) {
                    boolean found = false;

                    for (RoomServiceVM roomService : roomServices) {
                        String servicesName = cateDAO.getCategoryById(Integer.parseInt(service)).getName();
                        if (roomService.getCategoryName().equals(servicesName)) {
                            found = true;
                            break;
                        }
                        i++;
                    }
                    if (!found) {
                        matchesServices = false;
                        break;
                    }
                }
                if (matchesServices) {
                    filteredRooms.add(room);
                }
            }
            request.setAttribute("SERVICES", services.toArray());
            HomestayDAO homestayDAO = new HomestayDAO();
            Homestay homestay = homestayDAO.GetHomestayById(homestayId);
            request.setAttribute("HOMESTAY", homestay);
            request.setAttribute("ROOMS", filteredRooms);
            request.setAttribute("homestayId", homestayId);

            request.getRequestDispatcher(HOMESTAY_DETAIL).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UpdateHomestay(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("USER");
            Homestay homestay = new Homestay();
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String desc = request.getParameter("description");
            String ids = request.getParameter("homestayId");
            int homestayId = Integer.parseInt(ids);
            homestay.setHomestayId(homestayId);
            homestay.setAddress(address);
            homestay.setName(name);
            homestay.setDescription(desc);
            homestay.setUserId(userLogin.getId());

            HomestayDAO homestayDAO = new HomestayDAO();
            Homestay homestayUpdated = homestayDAO.updateHomeStay(homestay);
            if (homestayUpdated != null) {
                request.setAttribute("MESSAGE", "Cập nhật homestay thành công");
                request.setAttribute("homestayId", ids);
                viewDetailHomeStay(request, response);
            } else {
                RoomDAO roomDAO = new RoomDAO();
                Homestay homestayDetail = homestayDAO.GetHomestayById(homestayId);
                List<Room> listRoom = roomDAO.GetAllListRoomByHomeStayId(homestayId);

                request.setAttribute("HOME", homestayDetail);
                request.setAttribute("ROOMS", listRoom);
                request.setAttribute("ERROR", "Cập nhật homestay thất bại");
            }
            request.getRequestDispatcher(MANAGE_HOMESTAY_OWNER).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeStatusHomeStay(HttpServletRequest request, HttpServletResponse response) {
        try {
            String homestayId = request.getParameter("homestayId");
            if (homestayId != null) {
                HomestayDAO homestayDAO = new HomestayDAO();
                homestayDAO.updateHomeStayStatus(Integer.parseInt(homestayId));
                request.setAttribute("homestayId", homestayId);
                viewDetailHomeStay(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewDetailHomeStay(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String homestayIds = request.getParameter("homestayId");
            if (homestayIds != null) {
                int homestayId = Integer.parseInt(homestayIds);
                HomestayDAO homestayDAO = new HomestayDAO();
                RoomDAO roomDAO = new RoomDAO();

                Homestay homestayDetail = homestayDAO.GetHomestayById(homestayId);
                List<Room> listRoom = roomDAO.GetAllListRoomByHomeStayId(homestayId);
                List<Contract> listContract = roomDAO.getAllContract();

                List<Contract> listRoomContract = new ArrayList();
                
                for (int i = 0; i < listRoom.size(); i++) {
                    for (int j = 0; j < listContract.size(); j++) {
                        if (listRoom.get(i).getRoomId() == listContract.get(j).getRoomId()
                                && listRoom.get(i).getHomestayId() == listContract.get(j).getHomestayId()) {
                            listRoomContract.add(listContract.get(j));
                        }
                        listRoom.get(i).setContracts(listRoomContract);
                    }
                    listRoomContract = new ArrayList<Contract>();
                }
                

                if (session != null && session.getAttribute("ERROR") != null) {
                    String message = (String) session.getAttribute("ERROR");
                    request.setAttribute("ERROR", message);
                    session.removeAttribute("ERROR");
                }
                request.setAttribute("HOME", homestayDetail);
                request.setAttribute("ROOMS", listRoom);
                request.getRequestDispatcher(MANAGE_HOMESTAY_OWNER).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
