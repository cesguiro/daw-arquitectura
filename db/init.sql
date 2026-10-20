-- Se ejecuta solo la primera vez (cuando el volumen db-data está vacío)

CREATE TABLE IF NOT EXISTS mensajes (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    autor     VARCHAR(100) NOT NULL,
    texto     VARCHAR(500) NOT NULL,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO mensajes (autor, texto) VALUES
    ('cesguiro', 'Arquitectura de aplicación: nginx delante, Tomcat detrás'),
    ('nginx',    'Yo sirvo el frontend estático en /'),
    ('tomcat',   'Y yo la API REST; solo exponéis /api/ gracias al proxy'),
    ('mariadb',  'La persistencia relacional vive en la red interna');

-- Usuario exclusivo de la aplicación (mejor que conectar como root)
CREATE USER IF NOT EXISTS 'app'@'%' IDENTIFIED BY 'app123';
GRANT ALL PRIVILEGES ON miapp.* TO 'app'@'%';
FLUSH PRIVILEGES;
