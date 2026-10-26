package es.cesguiro.daw.arquitectura.api;

import es.cesguiro.daw.arquitectura.db.Mensaje;
import es.cesguiro.daw.arquitectura.db.MensajeRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MensajeController {

    private final MensajeRepository repository;

    public MensajeController(MensajeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/mensajes")
    public List<Mensaje> mensajes() {
        return repository.findAll();
    }
}
