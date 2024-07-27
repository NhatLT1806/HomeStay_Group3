<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

        <title>Hợp đồng thuê phòng</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }

        .contract-container {
            width: 80%;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }

        .header {
            text-align: center;
            margin-bottom: 20px;
        }

        .header p {
            margin: 0;
            font-size: 14px;
        }

        .header h1 {
            margin: 0;
            font-size: 20px;
            font-weight: bold;
        }

        .header h2 {
            margin: 5px 0;
            font-size: 18px;
        }

        .header h3 {
            margin: 10px 0;
            font-size: 24px;
            font-weight: bold;
        }

        .content p {
            margin: 10px 0;
            font-size: 16px;
        }

        .content h4 {
            margin-top: 20px;
            font-size: 18px;
            font-weight: bold;
        }

        .content h5 {
            margin: 10px 0;
            font-size: 16px;
            font-weight: bold;
        }

        .content ul {
            margin: 0 0 20px 20px;
            padding: 0;
            list-style-type: disc;
        }

        .footer {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
        }

        .signature {
            width: 40%;
            text-align: center;
        }

    </style>
    <jsp:include page="header.jsp" />
    <body>
        <div class="contract-container">
            <div class="header">
                <h1>CỘNG HÒA XÃ HỘI CHŨ NGHĨA VIỆT NAM</h1>
                <h2>Độc lập - Tự do - Hạnh phúc</h2>
                <h3>HỢP ĐỒNG THUÊ PHÒNG TRỌ</h3>
            </div>
            <div class="content">
                <p>Hôm nay ngày ... tháng ... năm .... tại địa chỉ ..............................................</p>
                <p>Chúng tôi gồm:</p>
                <p>- Đại diện bên cho thuê phòng trọ (Bên A):</p>
                <p>Họ và tên: <b>${OWNER.firstName} ${OWNER.lastName}</b></p>
                <p>Email: <b>${OWNER.email} </b><p>
                <p>Sinh ngày: ................</p>
                <p>CMND số: ................ cấp ngày .../.../.... tại ................................</p>
                <p>Số điện thoại: <b>${OWNER.phone}</b></p>
                <p>- Đại diện cho bên thuê trọ (Bên B):</p>
                <p>Họ và tên: <b>${CUSTOMER.firstName} ${CUSTOMER.lastName}</b></p>
                <p>Email: <b>${CUSTOMER.email} </b><p>
                <p>Sinh ngày: ................</p>
                <p>CMND số: ................ cấp ngày .../.../.... t?i ................................</p>
                <p>Số điện thoại:<b> ${USER.phone}</b> <p>
                <p>Sau khi bàn bạc trên tinh thần dân chủ, hai bên cùng có lợi, cùng thống nhất như sau:</p>
                <p>Bên A đồng ý cho bên B thuê 01 phòng trọ tại địa chỉ<b> ${HOMESTAY.address}</b></p>
                <p>Giá thuê:<b> ${ROOM.price}</b> đồnng/tháng</p>
                <p>Diện tích ${ROOM.area} m2</p>
                <p>Cho phép ở tối đa:<b> ${ROOM.maxParticipants} người</b> </p>
                <p>Hình thức thanh toán: .......................................................</p>
                <p>Tiền điện: .............. đ/kwh tính theo chỉ số công ty, thanh toán vào cuối các tháng.</p>
                <p>Tiền nước: .............. đ/m3 thanh toán vào cuối các tháng.</p>
                <p>Các khoản khác: ..........................................................................</p>
                <p>Hợp đồng có giá trị từ ngày ... tháng ... năm 20... nhầm ngày ... tháng ... năm 20...</p>
                <h4>TRÁCH NHIỆM</h4>
                <h5>Trách nhiệm của bên A:</h5>
                <ul>
                    <li>Đảm bảo quyền lợi cũng như sử dụng phòng trọ đúng như yêu cầu cho bên B</li>
                    <li>Cung cấp điện nước, internet, nội thất cho bên B.</li>
                </ul>
                <h5>Trách nhiệm của bên B:</h5>
                <ul>
                    <li>Chấp hành pháp luật Việt Nam và nội quy phòng trọ.</li>
                    <li>Thanh toán các khoản chi phí đã thỏa thuận</li>
                    <li>Giữ gìn vệ sinh chung, và tài sản của phòng trọ</li>
                </ul>
                <h4>NHỮNG ĐIỀU KHOẢN KHÁC</h4>
                <ul>
                    <li>Hợp đồng chia làm 2 bản, bên A và bên B mỗi người 1 bản.</li>
                    <li>Nếu có chi phí phát sinh thì 2 bên sẽ thỏa thuận lại.</li>
                    <li>Bên nào vi phạm hợp đồng sẽ phải bồi thường 100% giá trị hợp đồng cho bên còn lại</li>
                </ul>
            </div>
            <div class="footer" style="padding-bottom: 200px;">
                <div class="signature">
                    <p>ĐẠI DIỆN BÊN A</p>
                    <div class="signature">

                    </div>
                </div>
                <div class="signature">
                    <p>ĐẠI DIỆN BÊN B</p>
                </div>
            </div>
        </div>
    </body>
    <jsp:include page="footer.jsp" />
</html>
