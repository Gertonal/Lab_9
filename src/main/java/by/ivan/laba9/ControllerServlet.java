package by.ivan.laba9;

import java.io.BufferedReader;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet(name = "ControllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ModelLibrary model;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        model = new ModelLibrary();
        gson = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("getAll".equals(action)) {
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(model.getAllBooks()));
        } else {
            request.getRequestDispatcher("view.html").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        Book book = gson.fromJson(sb.toString(), Book.class);
        model.addBook(book);

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(model.getAllBooks()));
    }
}