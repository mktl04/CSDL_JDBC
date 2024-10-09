
package com.mycompanyservlet.curdservlet1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 *
 * @author DELL
 */
@WebServlet(name = "SaveServlet", urlPatterns = {"/SaveServlet"})
public class SaveServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //B1. Lấy giá trị tham số từ client
            String uname = request.getParameter("uname");
            String upass = request.getParameter("upass");
            String email = request.getParameter("email");
            String country = request.getParameter("country");
            //B2. Xử lí yêu cầu
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                //1. Nạp driver
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //2. Thiết lập kết nối CSDL
                conn = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-48GHHT8;databaseName=demodb","sa","C23");
                //System.out.println("Ket noi OK");
                ps = conn.prepareStatement("insert into users(name, password, email, country) values(?,?,?,?)");
                //3. Truyền giá trị 
                ps.setString(1, uname);
                ps.setString(2,upass);
                ps.setString(3, email);
                ps.setString(4, country);
                //4. Thi hành truy vấn
                int kq = ps.executeUpdate();
                //5. Xử lí kết quả trả về
                if(kq>0){
                    out.println("<h2>Thêm user thành công</h2>");
                } else {
                    out.println("<h2>Thêm user thất bại</h2>");
                }
                //6. Đóng kết nối
                conn.close();
            }catch (Exception e){
                System.out.println("Loi: " + e.toString());
                out.println("<h2>Thêm user thất bại</h2>");
            }
            request.getRequestDispatcher("index.html").include(request, response);
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SaveServlet</title>");
            out.println("</head>");
            out.println("<body>");
            //out.println("<h1>Servlet SaveServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
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
