package es.cesguiro.daw.arquitectura.db;

import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class MensajeRepository {

    private final JdbcClient jdbc;

    public MensajeRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<Mensaje> findAll() {
        return jdbc.sql("""
                        SELECT id, autor, texto, creado_en
                        FROM mensajes
                        ORDER BY id
                        """)
                .query((rs, rowIndex) -> new Mensaje(
                        rs.getLong("id"),
                        rs.getString("autor"),
                        rs.getString("texto"),
                        rs.getTimestamp("creado_en").toLocalDateTime()))
                .list();
    }
}
