package ru.javawebinar.topjava.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        MealsUtil.generation();
        log.debug("generation meals is done");

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime dateTime = LocalDateTime.of(1986, Month.APRIL, 8, 12, 30);
//        String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"

        req.setAttribute("listMeals", MealsUtil.getFilteredWithExceeded(MealsUtil.getMeals(),LocalTime.MIN,LocalTime.MAX,2000));
        req.setAttribute("formater", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/meals.jsp");
        // Forward (перенаправить) запрос, чтобы отобразить данные на странице JSP.
        dispatcher.forward(req, resp);
    }
}
