import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import exception.ProductDaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Warehouse;
import service.WarehouseService;
import service.WarehouseServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/warehouse")
public class WarehouseServlet extends HttpServlet {

    WarehouseService warehouseService = new WarehouseServiceImpl();
    ObjectMapper mapper = new ObjectMapper();

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String id = req.getParameter("id");
        {
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if (id == null) {
                out.print(mapper.writeValueAsString(warehouseService.getAllWarehouses()));
            } else {
                int idForGet;
                try {
                    idForGet = Integer.parseInt(id);
                    out.print(mapper.writeValueAsString(warehouseService.getWarehouse(idForGet)));
                    out.flush();
                } catch (NumberFormatException e) {
                    resp.sendError(400, "incorrect warehouse id");
                }
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Warehouse warehouse = mapper.readValue(req.getInputStream(), Warehouse.class);
            warehouseService.addWarehouse(warehouse);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(warehouse);
            out.flush();
        } catch (PropertyBindingException e) {
            resp.sendError(400, "incorrect warehouse");
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
            resp.sendError(400, "incorrect warehouse id");
            return;
        }
        try {
            warehouseService.deleteWarehouse(idForDelete);
        } catch (ProductDaoException e){
            resp.sendError(400, "incorrect warehouse id");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Warehouse warehouse = mapper.readValue(req.getInputStream(), Warehouse.class);
            warehouseService.updateWarehouse(warehouse);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(warehouse);
            out.flush();
        } catch (PropertyBindingException e) {
            resp.sendError(400, "incorrect warehouse");
        }
    }
}
