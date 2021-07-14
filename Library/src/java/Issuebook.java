/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ironhe_ad
 */
public class Issuebook extends HttpServlet {

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
            out.println("<title>Issuebook</title>");            
            out.println("</head>");
            out.println("<body>");
            
            HttpSession session=request.getSession(false);  
            String iname =  (String)session.getAttribute("globaluname");
            String s = (String)session.getAttribute("globalid");
            String status = (String)session.getAttribute("globalstatus");
            int iid = Integer.parseInt(s);
            
            try{
                    Class.forName("com.mysql.jdbc.Driver");
                    
                    java.util.Date date=new java.util.Date();
                    java.sql.Date sqlDate=new java.sql.Date(date.getTime());
                    java.sql.Date fsqld =new java.sql.Date(sqlDate.getTime()+24*60*60*1000*15);
                    
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
                    
                    Statement st = con.createStatement();
                    PreparedStatement pst1 = con.prepareStatement("UPDATE books SET Quantity = Quantity - 1 WHERE Book_ID = ?");
                    PreparedStatement pst = con.prepareStatement("INSERT INTO issued (Person_ID, Person_Name, Book_ID, Book_Name, Issue_Date, Return_Date, Status) values(?,?,?,?,?,?,?)");

                    String click = request.getParameter("btn1");
                    String ibookname = "";
                    int flag=0,i=0,j=0;
                    
                        if(click.equals("Issue")){
                        int ibookid = Integer.parseInt(request.getParameter("ibid"));
                        ResultSet rs = st.executeQuery("select * from books");
                        while(rs.next())
                        {
                            if(rs.getInt(1)==ibookid){
                               ibookname = rs.getString(2);
                               flag=rs.getInt(5);
                               break;
                            }
                        }
                        
                        if(flag!=0){
                            pst.setInt(1, iid);
                            pst.setString(2, iname);
                            pst.setInt(3, ibookid);
                            pst.setString(4, ibookname);
                            pst.setDate(5, sqlDate);
                            pst.setDate(6, fsqld);
                            pst.setString(7, "Issued");
                            pst1.setInt(1, ibookid);
                            
                            i = pst.executeUpdate(); 
                            j = pst1.executeUpdate();
                        }  
                           
                                       
                        if(i==0){
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                                    out.println("_____________________________________________________________________________________Error______________________________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Issuebookview.html");
                            rd.include(request, response);
                        }
                        else if(i!=0){
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                                    out.println("___________________________________________________________________________________Succesfull____________________________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Issuebookview.html");
                            rd.include(request, response);
                        }
                        }
                        else if(click.equals("Home")){
                            if(status.equals("student")){
                                RequestDispatcher rd = request.getRequestDispatcher("Home");
                                rd.forward(request, response);
                            }
                            else if(status.equals("admin")){
                                RequestDispatcher rd = request.getRequestDispatcher("Adminhome");
                                rd.forward(request, response);
                            }
                        }
                    
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
