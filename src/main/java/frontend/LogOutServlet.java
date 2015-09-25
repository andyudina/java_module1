package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class LogOutServlet extends HttpServlet {
    private AccountService accountService;

    public LogOutServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

 
        HttpSession session = request.getSession();
        String sessionId = session.toString();
        String login = (String) session.getAttribute("bestGameEverLogin");
        if (login == null || accountService.getSessions(sessionId) == null) {
            response.sendRedirect("/authform");
        } 
        else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("Login", login);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(PageGenerator.getPage("logout.html", pageVariables));
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        //Object logout = request.getParameter("logout");
        if (request.getParameter("logout") != null) {
            HttpSession session = request.getSession();
            String sessionId = session.toString();
            accountService.deleteSession(sessionId); 
        }
        response.sendRedirect("/authform");
    }
}
