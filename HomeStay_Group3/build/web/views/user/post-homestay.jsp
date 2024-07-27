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
                    <input type="hidden" name="action" value="post"/>
                    <h1>Tạo nhà</h1>
                    <label style="color: green">${MESSAGE}</label>     
                    <label style="color: red">${ERROR}</label>

                    <div class="dividerSocial"></div>

                    <div class="textField">
                        <label>Tên nhà</label>
                        <input type="text" name="name" />
                        <span class="error"></span>
                    </div>

                    <div class="textField">
                        <label>Địa chỉ</label>
                        <input name="address" type="text" />
                        <span class="error"></span>
                    </div>
                    <div class="textField">
                        <label>Mô tả</label>

                        <textarea id="description" name="description" style="height: 150px"></textarea>
                        <span class="error"></span>
                    </div>

                    <div>
                        Hệ thống sẽ thu phí là <b>1 lượt</b> trên 1 thêm nhà, số lượt sẽ được trừ sau khi quản trị viên phê duyệt yêu cầu của bạn.
                    </div>
                    <div>
                        <input type="checkbox" name="checkbox" id="checkbox_id" value="value" required>
                        <label for="checkbox_id">Bạn đã chắc muốn thêm nhà này ?</label>
                    </div>
                    <button type="submit">Tạo</button>

                    <a href="room?action=post" style="text-decoration: none;
                       text-align: center;
                       padding: 10px;
                       width: 200px;
                       display: block;
                       color: #fff;
                       background-color: #007bff">Thêm phòng</a>
                </div>


                <span class="divider"></span>
                <input type="file" accept="image/*" name="image" id="image-input" class="registerButton" required style="display: none"  />
                <div class="loginsForm">
                    <h3>Thêm ảnh cho nhà</h3>
                    <img  alt="Profile picture" id="profile-picture" class=" border-3 border-green-500 p-1 mb-3" style="width: 400px; cursor: pointer; margin: 10px auto;">
                </div>
        </form>
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
        if (!name || !address || !description || !imageInput.value) {
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