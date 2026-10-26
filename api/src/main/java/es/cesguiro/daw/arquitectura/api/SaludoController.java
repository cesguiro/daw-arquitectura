package es.cesguiro.daw.arquitectura.api;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoController {

    @GetMapping("/api/saludo")
    public Map<String, String> saludo(@RequestParam(defaultValue = "mundo") String nombre) {
        Map<String, String> respuesta = new LinkedHashMap<>();
        respuesta.put("saludo", "Hola, " + nombre + "!");
        respuesta.put("atendido_por", "Tomcat (Spring Boot) detrás del proxy nginx");
        respuesta.put("hora", LocalDateTime.now().toString());
        return respuesta;
    }
}
