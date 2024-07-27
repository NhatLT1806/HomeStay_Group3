<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Timeshare</title>
        <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
            />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
            />
    </head>
    <body>
        <div class="flex">
            <div class="w-2/12 bg-gray-800 text-white min-h-screen p-4">
                <div class="mb-4">
                    <h1 class="text-2xl font-bold flex items-center p-4">
                        <i class="fas fa-building mr-2"></i>Timeshare
                    </h1>
                </div>
                <nav class="space-y-2">
                    <a
                        href="#"
                        class="block py-2 px-4 rounded text-sm text-white flex items-center bg-blue-600"
                        >
                        <i class="fas fa-clipboard mr-2"></i>Homestay Management
                    </a>
                    <a
                        href="GetAllUser"
                        class="block py-2 px-4 rounded text-sm flex items-center hover:bg-gray-700"
                        >
                        <i class="fas fa-user mr-2"></i>User Management
                    </a>
                    <a
                        href="${pageContext.request.contextPath}/auth?action=logout"
                        class="block py-2 px-4 rounded text-sm hover:bg-gray-700 flex items-center"
                        >
                        <i class="fas fa-sign-out-alt mr-2"></i>Logout
                    </a>
                </nav>
            </div>
            <div class="w-9/12 p-8">
                <div class="flex justify-between mb-4">
                    <h2 class="text-2xl font-bold">Timeshare Management</h2>
                    <div class="relative">
                        <input
                            type="text"
                            class="border border-gray-300 rounded pl-8 pr-4 py-2"
                            placeholder="Search with title..."
                            />
                        <i
                            class="fas fa-search absolute top-1/2 transform -translate-y-1/2 left-2 text-gray-400"
                            ></i>
                    </div>
                </div>
                <div class="grid grid-cols-4 gap-4">
                    <!-- Card 1 -->
                    <c:forEach items="${HOMESTAYS}" var="homestay">
                        <div class="bg-white rounded-lg shadow-md overflow-hidden">
                            <img
                                src="data:image/png;base64,${homestay.image}"
                                alt="Image"
                                class="w-full"
                                />
                            <div class="p-4">
                                <h3 class="text-xl font-bold">
                                    Name: ${homestay.name}
                                </h3>
                                <p class="text-gray-600">
                                    Owner: ${homestay.user.firstName} ${homestay.user.lastName}
                                </p>

                                <p class="text-gray-600">
                                    Address: ${homestay.address}
                                </p>
                            </div>
                            <div class="flex justify-between p-4">


                                <a  data-bs-toggle="modal" data-bs-target="#accepte-${homestay.homestayId}" aria-hidden="true"
                                    class="text-green-500" style="cursor: pointer">
                                    <i class="fas fa-check-circle text-2xl"></i>
                                </a>

                                <div class="modal fade" id="accepte-${homestay.homestayId}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" >Bạn có chắc cho phép nhà trọ này xuất hiện ở homepage</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                Hành động này sẽ cho phép nhà trọ này xuất hiện ở homepage. Bạn sẽ không thể hoàn tác hành động trên.
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <a class="btn btn-success"  href="manage-homestay?action=accept&id=${homestay.homestayId}&userId=${homestay.userId}" >Xác nhận</a>
                                            </div>
                                        </div>
                                    </div>
                                </div> 


                                <a  data-bs-toggle="modal" data-bs-target="#reject-${homestay.homestayId}" aria-hidden="true"
                                    class="text-red-500" style="cursor: pointer">
                                    <i class="fas fa-times-circle text-2xl"></i>
                                </a>

                                <div class="modal fade" id="reject-${homestay.homestayId}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" >Bạn có chắc không đồng ý nhà trọ này xuất hiện ở homepage</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                Hành động này sẽ  KHÔNG cho phép nhà trọ này xuất hiện ở homepage. Bạn sẽ không thể hoàn tác hành động trên.
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <a class="btn btn-danger"  href="manage-homestay?action=accept&id=${homestay.homestayId}&userId=${homestay.userId}" >Xác nhận</a>
                                            </div>
                                        </div>
                                    </div>
                                </div> 


                                <a class="text-blue-500" style="cursor: pointer"  data-bs-toggle="modal" data-bs-target="#info-${homestay.homestayId}" aria-hidden="true">
                                    <i class="fas fa-info-circle text-2xl"></i>
                                </a>


                                <div class="modal fade" id="info-${homestay.homestayId}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" >Thông tin nhà trọ</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div>
                                                    <p class="timeshare-details-title">
                                                        <b>Tên nhà  </b>${homestay.name}
                                                    <p>
                                                    <p >
                                                        <b>Địa chỉ: </b> ${homestay.address} 
                                                    </p>
                                                    <p >
                                                        <b>Mô tả: </b> ${homestay.description} 
                                                    </p>
                                                    <p >
                                                        <b>Chủ nhà </b>  ${homestay.user.firstName}   ${homestay.user.lastName} 
                                                    </p>
                                                    <p >
                                                        <b>Email:  </b>  ${homestay.user.email}
                                                    </p>
                                                    <p >
                                                        <b>Phone:  </b>  ${homestay.user.phone}
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            </div>
                                        </div>
                                    </div>
                                </div> 



                            </div>
                        </div>
                    </c:forEach>

                </div>
                <div class="mt-4 flex justify-end">
                    <nav
                        class="relative z-0 inline-flex shadow-sm -space-x-px"
                        aria-label="Pagination"
                        >
                        <a
                            href="#"
                            class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
                            >
                            <span class="sr-only">Previous</span>
                            <
                        </a>
                        <a
                            href="#"
                            class="z-10 bg-indigo-50 border-indigo-500 text-indigo-600 relative inline-flex items-center px-4 py-2 border text-sm font-medium"
                            >1</a
                        >
                        <a
                            href="#"
                            class="bg-white border-gray-300 text-gray-500 hover:bg-gray-50 relative inline-flex items-center px-4 py-2 border text-sm font-medium"
                            >2</a
                        >
                        <a
                            href="#"
                            class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
                            >
                            <span class="sr-only">Next</span>
                            >
                        </a>
                    </nav>
                </div>
            </div>
        </div>
    </body>
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"
    ></script>
</html>
