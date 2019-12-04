package org.learningredis.chapter.six.web;

import org.learningredis.chapter.six.web.productmgmt.commands.*;
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
public class ProductApp extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        Argument argument = new Argument(req.getParameter("args"));
        PrintWriter out = resp.getWriter();
        switch (command.toLowerCase()) {
            case "commission":
                Commands commission = new CommissionProductCommand(argument);
                out.println(commission.execute());
                break;
            case "display":
                Commands display = new DisplayCommand(argument);
                out.println(display.execute());
                break;
            case "displaytag":
                Commands displayTag = new DisplayTagCommand(argument);
                out.println(displayTag.execute());
                break;
            case "updatetag":
                Commands updateTag = new UpdateTagCommand(argument);
                out.println(updateTag.execute());
                break;
            case "visitstoday":
                Commands visitsToday = new VisitTodayCommand(argument);
                out.println(visitsToday.execute());
                break;
            case "purchasestoday":
                Commands purchasesToday = new PurchasesTodayCommand(argument);
                out.println(purchasesToday.execute());
                break;
            case "taghistory":
                Commands tagHistory = new TagHistoryCommand(argument);
                out.println(tagHistory.execute());
                break;
            default:
                Commands defaultUC = new DefaultCommand(argument);
                out.println(defaultUC.execute());
                break;
        }
    }
}
