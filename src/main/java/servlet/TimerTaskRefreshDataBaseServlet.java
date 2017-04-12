package servlet;

import concurrent_tasks.ExecuteTimerTask;
import concurrent_tasks.RefreshInformationInDataBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by AZagorskyi on 07.04.2017.
 */
@WebServlet("/runTimer")
public class TimerTaskRefreshDataBaseServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse){
        TimerTask timerTask = new ExecuteTimerTask();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 10000, 100000);
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException {
        doGet(httpRequest, httpResponse);
    }
}
