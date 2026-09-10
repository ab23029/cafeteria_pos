package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.TipoDescuentoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoDescuento;

@WebServlet(name = "TipoDescuentoServlet", urlPatterns = {"/tipo-descuento"})
public class TipoDescuentoServlet extends HttpServlet {

    @Inject
    private TipoDescuentoDAOInterface tdDAO;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String nombre = request.getParameter("nombre");
        String observaciones = request.getParameter("observaciones");

        try (PrintWriter out = response.getWriter()) {
            if (nombre != null && !nombre.trim().isEmpty()) {
                TipoDescuento nuevo = new TipoDescuento();
                nuevo.setIdDescuento(UUID.randomUUID());
                nuevo.setNombre(nombre);
                nuevo.setObservaciones(observaciones);

                if (tdDAO != null) {
                    tdDAO.create(nuevo);
                    out.println("<h2>Guardado con éxito: " + nombre + "</h2>");
                } else {
                    out.println("<h2>Error: DAO no inyectado.</h2>");
                }
            } else {
                out.println("<h2>Error: El nombre es obligatorio.</h2>");
            }
            out.println("<a href='tipo-descuento.html'>Volver</a>");
        }
    }
}
