package sklep.alternatywne_dostepy_do_bazy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;

@Controller
@RequestMapping("/alt0/products")
public class ProductController_v0 {
    // W tej wersji samodzielnie nawiązuję połączenie z bazą

    @GetMapping(produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String readAll() {
        StringBuilder sb = new StringBuilder("Lista produktów:\n");
        try(Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sklep", "kurs", "abc123");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products ORDER BY product_id")) {

            while(rs.next()) {
                sb.append("* produkt ")
                        .append(rs.getString("product_name"))
                        .append(" za cenę ")
                        .append(rs.getBigDecimal("price"))
                        .append('\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sb.append("Błąd: ").append(e);
        }
        return sb.toString();
    }
}
