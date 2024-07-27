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
        <form action="room" method="POST" id="registrationForm"  enctype="multipart/form-data">
            <div class="registersform">
                <div class="flexsForm">
                    <input type="hidden" name="action" value="post"/>
                    <h1>Tạo phòng</h1>
                    <label style="color: green">${MESSAGE}</label>     
                    <label style="color: red">${ERROR}</label>

                    <div class="dividerSocial"></div>

                    <div class="textField">
                        <label>Tên phòng</label><input type="text" name="name" /><span class="error"></span>
                    </div>

                    <div class="textField">
                        <label>Diện tích</label><input name="area" type="number" /><span class="error"></span>
                    </div>
                    <div class="textField">
                        <label>Giá</label><input name="price" type="number" /><span class="error"></span>
                    </div>
                    <div class="textField">
                        <label>Cho phép ở tối đa</label><input name="limit" type="number" /><span class="error"></span>
                    </div>
                    <div class="textField">
                        <label>Dịch vụ</label >
                        <select name="selectedCategories" id="service"   multiple="multiple" style="height: 100px">
                            <c:forEach items="${CATEGORIES}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                        <span class="error"></span>
                    </div>
                    <div class="textField">
                        <label>Phòng</label>
                        <select name="homestayId" id="homestay"  >
                            <c:forEach items="${HOMESTAYS}" var="home">
                                <option value="${home.homestayId}">${home.name}</option>
                            </c:forEach>
                        </select >
                        <span class="error"></span>
                    </div>
                    <button type="submit">Tạo</button>

                </div>
                <span class="divider"></span>
                <input type="file" accept="image/*" name="image" id="image-input" class="registerButton" style="display: none" />
                <div class="loginsForm">
                    <h3>Thêm ảnh cho phòng</h3>
                    <img  alt="Profile picture" id="profile-picture" class=" border-3 border-green-500 p-1 mb-3" style="width: 400px; cursor: pointer; margin: 10px auto;">
                </div>
            </div>
        </form>
        <jsp:include page="footer.jsp" />

    </body>

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
            var sercviecs = document.getElementById('service').value;    
            var homestay = document.getElementById('homestay').value;

            var name = document.querySelector('[name="name"]').value;
            var area = document.querySelector('[name="area"]').value;
            var price = document.querySelector('[name="price"]').value;   
            var limit = document.querySelector('[name="limit"]').value;

            // Kiểm tra xem có trường nào bỏ trống không
            if (!name || !sercviecs || !homestay || !area || !price || !limit || !imageInput.value) {
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