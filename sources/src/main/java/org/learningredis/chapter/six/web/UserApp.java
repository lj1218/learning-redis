package org.learningredis.chapter.six.web;

import org.learningredis.chapter.six.web.sessionmgmt.commands.*;
import org.learningredis.chapter.six.web.util.Argument;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class UserApp extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionID;
        String command = req.getParameter("command");
        Argument argument = new Argument(req.getParameter("args"));
        PrintWriter out = resp.getWriter();
        switch (command.toLowerCase()) {
            case "register":
                Commands register = new RegistrationCommand(argument);
                out.println(register.execute());
                break;
            case "login":
                Commands login = new LoginCommand(argument);
                out.println(login.execute());
                break;
            case "mydata":
                Commands myData = new MyDataCommand(argument);
                out.println(myData.execute());
                break;
            case "editmydata":
                Commands editMyData = new EditMyDataCommand(argument);
                out.println(editMyData.execute());
                break;
            case "recommendbyproduct":
                Commands recommendByProduct = new RecommendByProductCommand(argument);
                out.println(recommendByProduct.execute());
                break;
            case "browse":
                Commands browse = new BrowseCommand(argument);
                out.println(browse.execute());
                String productName = argument.getValue("browse");
                sessionID = argument.getValue("sessionid");
                req.getRequestDispatcher(
                        "/productApp?command=updatetag&args=sessionid=" + sessionID
                                + ":productname=" + productName
                                + ":action=browse").include(req, resp);
                break;
            case "buy":
                Commands buy = new BuyCommand(argument);
                String[] details = buy.execute().split("#");
                out.println(details[0]);
                sessionID = argument.getValue("sessionid");
                req.getRequestDispatcher(
                        "/productApp?command=updatetag&args=sessionid=" + sessionID
                                + ":action=buy:details=" + details[1]).include(req, resp);
                break;
            case "stats":
                Commands stats = new MyStatusCommand(argument);
                out.println(stats.execute());
                break;
            case "add2cart":
                Commands add2cart = new Add2CartCommand(argument);
                out.println(add2cart.execute());
                break;
            case "showmycart":
                Commands showMyCart = new ShowMyCartCommand(argument);
                out.println(showMyCart.execute());
                break;
            case "editcart":
                Commands editCart = new EditCartCommand(argument);
                out.println(editCart.execute());
                break;
            case "relogin":
                Commands reLogin = new ReLoginCommand(argument);
                out.println(reLogin.execute());
                break;
            case "logout":
                Commands logout = new LogoutCommand(argument);
                out.println(logout.execute());
                break;
            case "mypurchasehistory":
                Commands myPurchaseHistory = new MyPurchaseHistoryCommand(argument);
                out.println(myPurchaseHistory.execute());
                break;
            default:
                Commands defaultUC = new DefaultCommand(argument);
                out.println(defaultUC.execute());
                break;
        }
    }
}
