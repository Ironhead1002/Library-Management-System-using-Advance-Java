/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ironhe_ad
 */
public class Yourissuedbooks extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Yourissuedbooks</title>"); 
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
            
            out.println("</head>");
            out.println("<body background=\"https://images.wallpaperscraft.com/image/books_library_shelves_138556_1920x1080.jpg\">");            
            out.println("</br></br></br></br><p><a href=\"Userselect\" class=\"btn text-primary btn-lg btn-block\">Home</a></p>");
            HttpSession session=request.getSession(false);  
            String s = (String)session.getAttribute("globalid");
            int iid = Integer.parseInt(s);
            
            try{
                    out.println("<form action=\"Yourissuedbooks\" method=\"post\">");
                    Class.forName("com.mysql.jdbc.Driver");
                    
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
                    PreparedStatement pst = con.prepareStatement("select * from issued where Person_ID = ?");
                    
                    pst.setInt(1,iid);
                    ResultSet rs = pst.executeQuery();
                    
                    out.println("<center>");
                    out.println("<table border=5 width=80% height=50% class=\"p-3 mb-2 bg-primary text-light\">");  
                    out.println("<tr><th>Issue_Id</th><th>Person_ID</th><th>Person_Name</th><th>Book_ID</th><th>Book_Name</th><th>Issued_On</th><th>Return_Date</th><th>Status</th><tr>");
                out.println("</center>");
                    while(rs.next()) 
                    {
                        int issueid = rs.getInt(1);
                        int personid = rs.getInt(2);
                        String personname = rs.getString(3);
                        int bookid = rs.getInt(4);
                        String bookname = rs.getString(5);
                        String issuedate = rs.getString(6);
                        String returndate = rs.getString(7);
                        String status = rs.getString(8);
                        
                        out.println("<tr><td>" + issueid + "</td><td>" + personid + "</td><td>" + personname + "</td><td>" + bookid + "</td><td>" + bookname + "</td><td>" + issuedate + "</td><td>" + returndate + "</td><td>" + status + "</td></tr>");   
                    }
                    out.println("</table>");
                    
            }catch(Exception e){
                out.println(e);
            }
            
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
