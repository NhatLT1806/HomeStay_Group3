<%@ page import="java.net.URLEncoder" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>HomeStay</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/post-timeshare.css">
        <!-- Google Web Fonts -->
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <form action="homestay" method="POST" id="registrationForm" enctype="multipart/form-data">
            <div class="registersform">
                <div class="flexsForm">
                    <input type="hidden" name="action" value="update"/>  
                    <input type="hidden" name="homestayId" value="${HOME.homestayId}"/>

                    <h1>Quản lí nhà: ${HOME.name}</h1>
                    <label style="color: green">${MESSAGE}</label>     
                    <label style="color: red">${ERROR}</label>

                    <div class="dividerSocial"></div>

                    <div class="textField">
                        <label>Tên nhà</label>
                        <input type="text" name="name" value="${HOME.name}"/>
                        <span class="error"></span>
                    </div>

                    <div class="textField">
                        <label>Địa chỉ</label>
                        <input name="address" type="text" value="${HOME.address}" />
                        <span class="error"></span>
                    </div>
                    <div class="textField">
                        <label>Mô tả</label>

                        <textarea id="description" name="description" style="height: 150px">${HOME.description}</textarea>
                        <span class="error"></span>
                    </div>

                    <div class="textField">
                        <label>Trạng thái:</label>
                        <c:if test="${HOME.status == 1}">
                            <h4 style="color: green">Hiện ở trang homepage</h4>
                        </c:if>
                        <c:if test="${HOME.status == 0}">
                            <h4 style="color: green">Đang chờ được duyệt</h4>
                        </c:if>
                        <c:if test="${HOME.status == 4}">
                            <h4 style="color: #00748f">Đã ẩn</h4>
                        </c:if>
                        <span class="error"></span>
                    </div>


                    <div style="display: flex; justify-content: space-between; align-items: center">
                        <button type="submit">Cập nhật</button>
                        <c:if test="${HOME.status != 0}">
                            <a href="homestay?action=change-status-homestay&homestayId=${HOME.homestayId}" class="btn btn-danger">Đổi trạng thái</a>
                        </c:if>
                    </div>

                    <a href="room?action=post" style="text-decoration: none;
                       text-align: center;
                       padding: 10px;
                       width: 200px;
                       display: block;
                       color: #fff;
                       background-color: #007bff">Thêm phòng</a>
                </div>


                <span class="divider"></span>
                <input type="file" accept="image/*" value="${HOME.image}" name="image" id="image-input" class="registerButton" required style="display: none"  />
                <div class="loginsForm">
                    <h3>Ảnh</h3>
                    <img src="data:image/png;base64,${HOME.image}"  alt="Profile picture" id="profile-picture" class=" border-3 border-green-500 p-1 mb-3" style="width: 400px; cursor: pointer; margin: 10px auto;">
                </div>
        </form>


    </div>
    <div>

        <div style="margin: 20px 0 20px 200px">
            <h1> Danh sách phòng của nhà: ${HOME.name} </h1>
        </div>

        <c:forEach items="${ROOMS}" var="r">
            <form action="room" method="POST" id="registrationForm" enctype="multipart/form-data">
                <div class="registersform">
                    <div class="flexsForm">
                        <input type="hidden" name="action" value="update"/>     
                        <input type="hidden" name="roomId" value="${r.roomId}"/>

                        <h1>Phòng: ${r.name}</h1>
                        <label style="color: green">${MESSAGE}</label>     
                        <label style="color: red">${ERROR}</label>

                        <div class="dividerSocial"></div>

                        <div class="textField">
                            <label>Tên phòng</label><input type="text" name="name" value="${r.name}"/><span class="error"></span>
                        </div>

                        <div class="textField">
                            <label>Diện tích</label><input name="area" type="text" value="${r.area}"/><span class="error"></span>
                        </div>
                        <div class="textField">
                            <label>Giá</label><input name="price" type="text" value="${r.price}"/><span class="error"></span>
                        </div>
                        <div class="textField">
                            <label>Cho phép ở tối đa</label><input name="limit" type="text" value="${r.maxParticipants}" /><span class="error"></span>
                        </div>

                        <div class="textField">
                            <label>Trạng thái:</label>
                            <c:if test="${r.status == 1}">
                                <h4 style="color: green">Sẵn sàng cho thuê</h4>
                            </c:if>
                            <c:if test="${r.status == 0}">
                                <h4 style="color: red">Đã ẩn</h4>
                            </c:if>
                            <c:if test="${r.status == 2}">
                                <h4 style="color: green">Đã có người thuê</h4>
                            </c:if>
                            <span class="error"></span>
                        </div>


                        <div style="display: flex; justify-content: space-between; align-items: center">
                            <button type="submit">Cập nhật</button>
                            <c:if test="${r.status != 2}">
                                <a href="room?action=change-status-room&roomId=${r.roomId}&homestayId=${HOME.homestayId}" class="btn btn-danger">Đổi trạng thái</a>
                            </c:if>
                            <c:if test="${r.status == 2}">
                                <a href="room?action=update-room-status&roomId=${r.roomId}&homestayId=${HOME.homestayId}" class="btn btn-success">Cho phép thuê lại</a>
                            </c:if>
                        </div>
                        <div>
                            Danh sách hợp đồng
                            <c:forEach items="${r.contracts}" var="contract" varStatus="status">

                                <div style="display: flex; justify-content: space-between; margin-top: 20px">
                                    <div>
                                        <b>${status.count}: </b>
                                        <a href="contract?roomId=${contract.roomId}&contractId=${contract.contractId}" class="btn btn-success">Xem hợp đồng</a>
                                    </div>
                                    Trạng thái:
                                    <c:if test="${contract.status == 0}">
                                        Đang chờ xác nhận
                                    </c:if>
                                    <c:if test="${contract.status == 1}">
                                        Thành công
                                    </c:if>
                                    <c:if test="${contract.status == 2}">
                                        Thất bại
                                    </c:if>

                                    <c:if test="${contract.status == 0}">
                                        <a href="room?action=update-contract-status&roomId=${contract.roomId}&homestayId=${HOME.homestayId}&contractId=${contract.contractId}&type=approve" class="btn btn-success btn-sm">Hoàn thành</a>   
                                        <a href="room?action=update-contract-status&roomId=${contract.roomId}&homestayId=${HOME.homestayId}&contractId=${contract.contractId}&type=reject" class="btn btn-danger btn-sm">Thất bại</a>
                                    </c:if>
                                </div>

                            </c:forEach>

                        </div>

                    </div>
                    <span class="divider"></span>
                    <input type="file"  accept="image/*" name="image" id="image-input" class="registerButton" style="display: none" />
                    <div class="loginsForm">
                        <h3>Thêm ảnh cho phòng</h3>
                        <img src="data:image/png;base64,${r.image}" alt="Profile picture" id="profile-picture" class=" border-3 border-green-500 p-1 mb-3" style="width: 400px; cursor: pointer; margin: 10px auto;">
                    </div>
                </div>
            </form>
        </c:forEach>

    </div>

    <jsp:include page="footer.jsp" />

</body>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    const profilePicture = document.getElementById('profile-picture');
    const imageInput = document.getElementById('image-input');

    profilePicture.addEventListener('click', () => {
        imageInput.disabled = false;
        imageInput.click();
    });
    const updateAvatar = false;
    imageInput.addEventListener('change', () => {
        imageInput.disabled = false;
        const file = imageInput.files[0];
        const formData = new FormData();
        formData.append('image', file);

        const reader = new FileReader();
        reader.onload = () => {
            profilePicture.src = reader.result;
        };
        reader.readAsDataURL(file);
        updateAvatar = true;
    });
    if (!updateAvatar && imageInput.value != null) {
        imageInput.disabled = true;
    }

    document.getElementById('registrationForm').addEventListener('submit', function (event) {
        // Ngăn chặn hành động mặc định của form khi submit
        event.preventDefault();

        // Kiểm tra các trường
        var name = document.querySelector('[name="name"]').value;
        var address = document.querySelector('[name="address"]').value;
        var description = document.querySelector('[name="description"]').value;

        // Kiểm tra xem có trường nào bỏ trống không
        if (!name || !address || !description) {
            // Sử dụng SweetAlert để thông báo lỗi
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Bạn phải điền đầy đủ thông tin vào tất cả các trường!'
            });
        } else {
            // Nếu mọi thứ ổn, submit form
            this.submit();
        }
    });

</script>
</html>