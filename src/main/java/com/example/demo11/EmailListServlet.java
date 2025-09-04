package com.example.demo11;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Calendar;

public class EmailListServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException
    {

        // Lấy tham số từ form
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String email     = request.getParameter("email");

        // Tạo đối tượng User
        User user = new User(firstName, lastName, email);

        // Lưu user vào session để giữ lại dữ liệu khi quay lại
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // Kiểm tra dữ liệu trống
        String message = "";
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                email == null || email.trim().isEmpty())
        {
            message = "All fields are required. Please fill out all fields.";
            request.setAttribute("message", message);

            // Quay lại index.jsp
            String url = "/index.jsp";
            getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
            return;
        }

        // Lấy năm hiện tại
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        request.setAttribute("currentYear", currentYear);

        // --> thanks.jsp
        String url = "/thanks.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException
    {
        // Lấy user từ session nếu có, nếu chưa có thì tạo mới
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            session.setAttribute("user", user);
        }

        request.setAttribute("user", user);

        String url = "/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
