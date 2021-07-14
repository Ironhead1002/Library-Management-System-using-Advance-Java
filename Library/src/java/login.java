/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ironhe_ad
 */
public class login extends HttpServlet {

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
            out.println("<title>login</title>");            
            out.println("</head>");
            out.println("<body >");
            
            try {
           
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
                
                Statement st = con.createStatement();
           
                String uname = request.getParameter("un");
                String pwd = request.getParameter("pass");
                String who = "";
                String pid = "";
                
                int flag = 0;
                
                ResultSet rs = st.executeQuery("select * from login");
                while(rs.next()) 
                {
                    if(uname.equals(rs.getString(2))&&pwd.equals(rs.getString(3)))
                    {
                        flag=1;
                        who = rs.getString(4);
                        pid = rs.getString(1);
                        break;
                    }
                    else
                    {
                        flag = 0;
                    }
                }
                if((flag == 1)&&who.equals("student")){
                    
                    HttpSession session=request.getSession();  
                    session.setAttribute("globaluname",uname); 
                    session.setAttribute("globalid",pid);
                    session.setAttribute("globalstatus",who);
                    
                    out.print("Given Username and Password are Correct!!!");
                    RequestDispatcher rd = request.getRequestDispatcher("Home");
                    rd.forward(request, response);
                }
                else if((flag == 1)&&who.equals("admin")){
                    
                    HttpSession session=request.getSession();  
                    session.setAttribute("globaluname",uname); 
                    session.setAttribute("globalid",pid);
                    session.setAttribute("globalstatus",who);
                    
                    out.print("Given Username and Password are Correct!!!"); 
                    RequestDispatcher rd = request.getRequestDispatcher("Adminhome");
                    rd.forward(request, response);
                }
                else{
                     out.println("<font color= white>");
                     out.println("<br>");
                     out.println("<strong>");
                     out.print("Given Username and Password are Incorrect!!!");
                     out.println("</strong>");
                     out.println("</font>");
                    RequestDispatcher rd = request.getRequestDispatcher("index.html");
                    rd.include(request, response);
                }
            }
            catch(Exception e){
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
