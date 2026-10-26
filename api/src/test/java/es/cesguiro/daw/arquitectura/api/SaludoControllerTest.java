package es.cesguiro.daw.arquitectura.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SaludoController.class)
class SaludoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saludaConElNombreRecibido() throws Exception {
        mockMvc.perform(get("/api/saludo").param("nombre", "Ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.saludo").value("Hola, Ana!"));
    }
}
