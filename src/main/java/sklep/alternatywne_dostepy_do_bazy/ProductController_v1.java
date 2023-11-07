package sklep.alternatywne_dostepy_do_bazy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.*;

@Controller
@RequestMapping("/alt1/products")
public class ProductController_v1 {
    @Autowired
    private DataSource dataSource;
    // W tej wersji Spring dostarcza nam obiekt DataSource na podstawie konfiguracji w pliku application.properties

    @GetMapping(produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String readAll() {
        StringBuilder sb = new StringBuilder("Lista produktów:\n");
        try(Connection c = dataSource.getConnection();
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
    
    @GetMapping("/{id}")
    @ResponseBody
    public String jedenProdukt(@PathVariable int id) {
        StringBuilder sb = new StringBuilder();        
        final String sql = "SELECT * FROM products WHERE product_id = ?";
        try(Connection c = dataSource.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    sb.append("Znaleziony produkt:\n")
                        .append(rs.getString("product_name"))
                        .append(" za cenę ")
                        .append(rs.getBigDecimal("price"))
                        .append('\n');
                } else {
                    sb.append("Nie ma produktu o numerze ").append(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sb.append("Błąd: " + e);
        }
        return sb.toString();
    }
    
    @GetMapping("/szukaj")
    @ResponseBody
    public String wyszukajProdukty(@RequestParam String name) {
        StringBuilder sb = new StringBuilder();
        
        final String sql = "SELECT * FROM products WHERE product_name = ? ORDER BY product_id";
        try(Connection c = dataSource.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, name);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    sb.append("* produkt ")
                        .append(rs.getString("product_name"))
                        .append(" za cenę ")
                        .append(rs.getBigDecimal("price"))
                        .append('\n');
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sb.append("Błąd: " + e);
        }
        return sb.toString();
    }
}
