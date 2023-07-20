package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import exception.ProductDaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import service.ProductService;
import service.ProductServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();
    ObjectMapper mapper = new ObjectMapper();

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String id = req.getParameter("id");
        {
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if (id == null) {
                out.print(mapper.writeValueAsString(productService.getAllProduct()));
            } else {
                int idForGet;
                try {
                    idForGet = Integer.parseInt(id);
                    out.print(mapper.writeValueAsString(productService.getProduct(idForGet)));
                    out.flush();
                } catch (NumberFormatException e) {
                    resp.sendError(400, "incorrect product id");
                }
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Product product = mapper.readValue(req.getInputStream(), Product.class);
            productService.addProduct(product);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(product);
            out.flush();
        } catch (PropertyBindingException e) {
            resp.sendError(400, "incorrect product");
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
            resp.sendError(400, "incorrect product id");
            return;
        }
        try {
            productService.deleteProduct(idForDelete);
        } catch (ProductDaoException e){
            resp.sendError(400, "incorrect product id");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Product product = mapper.readValue(req.getInputStream(), Product.class);
            productService.updateProduct(product);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(product);
            out.flush();
        } catch (PropertyBindingException e) {
            resp.sendError(400, "incorrect product");
        }
    }
}
