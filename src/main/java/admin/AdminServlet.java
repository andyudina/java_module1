package admin;
import main.TimeHelper;
import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class AdminServlet extends HttpServlet {
    //public static final String adminPageURL = "/admin";
    private AccountService accountService;

    public AdminServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        if (request.getParameterMap().containsKey("shutdown")) {
            String timeString;
            timeString = request.getParameter("timeString");
            int timeMS = 0;
            if (timeString != null) {
                 timeMS= Integer.valueOf(timeString);
            }
            TimeHelper.sleep(timeMS);
            System.out.print("\nShutdown");
            System.exit(0);
        }

        Map<String, Object> pageVariables = new HashMap<>();

        ArrayList<String> signedUpUsers = new ArrayList<>();
        ArrayList<String> logInUsers = new ArrayList<>();
        
        if (request.getParameterMap().containsKey("showuserlist")) {
            signedUpUsers =  accountService.getSignedUpUsers();
            logInUsers = accountService.getLogInUsers();
        }

        pageVariables.put("status", "run");
        pageVariables.put("signedUpUsers", signedUpUsers);
        pageVariables.put("signedInUsers", logInUsers);
        response.getWriter().println(PageGenerator.getPage("admin.html", pageVariables));
    }
}
