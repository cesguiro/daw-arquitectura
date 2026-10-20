package es.cesguiro.daw.arquitectura.db;

import java.time.LocalDateTime;

public record Mensaje(long id, String autor, String texto, LocalDateTime creadoEn) {
}
