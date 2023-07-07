import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import exception.ProductDaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Discount;
import service.DiscountService;
import service.DiscountServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/discount")
public class DiscountServlet extends HttpServlet {

    DiscountService discountService = new DiscountServiceImpl();
    ObjectMapper mapper = new ObjectMapper();

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String id = req.getParameter("id");
        {
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if (id == null) {
                out.print(mapper.writeValueAsString(discountService.getAllDiscounts()));
            } else {
                int idForGet;
                try {
                    idForGet = Integer.parseInt(id);
                    out.print(mapper.writeValueAsString(discountService.getDiscount(idForGet)));
                    out.flush();
                } catch (NumberFormatException e) {
                    resp.sendError(400, "incorrect discount id");
                }
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Discount discount = mapper.readValue(req.getInputStream(), Discount.class);
            discountService.addDiscount(discount);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(discount);
            out.flush();
        } catch (PropertyBindingException e) {
            resp.sendError(400, "incorrect discount");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if(id == null){
            resp.sendError(400, "null id");
            return;
        }
        int idForDelete;
        try {
            idForDelete = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            resp.sendError(400, "incorrect discount id");
            return;
        }
        try {
            discountService.deleteDiscount(idForDelete);
        } catch (ProductDaoException e){
            resp.sendError(400, "incorrect discount id");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Discount discount = mapper.readValue(req.getInputStream(), Discount.class);
            discountService.updateDiscount(discount);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(discount);
            out.flush();
        } catch (PropertyBindingException e) {
            resp.sendError(400, "incorrect discount");
        }
    }
}
