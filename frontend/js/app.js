const $ = (selector) => document.querySelector(selector);

async function pedirSaludo(event) {
    event.preventDefault();
    const nombre = ($("#nombre").value || "").trim() || "mundo";
    const salida = $("#saludo-respuesta");
    salida.textContent = "Cargando...";
    try {
        const res = await fetch(`/api/saludo?nombre=${encodeURIComponent(nombre)}`);
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        salida.textContent = JSON.stringify(await res.json(), null, 2);
    } catch (error) {
        salida.textContent = `Error: ${error.message}`;
    }
}

async function cargarMensajes() {
    const lista = $("#lista-mensajes");
    lista.innerHTML = "<li>Cargando...</li>";
    try {
        const res = await fetch("/api/mensajes");
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const mensajes = await res.json();
        lista.innerHTML = mensajes.map((m) =>
            `<li><strong>${m.autor}</strong> — ${m.texto}<small>${m.creadoEn}</small></li>`
        ).join("");
    } catch (error) {
        lista.innerHTML = `<li>Error: ${error.message}</li>`;
    }
}

$("#form-saludo").addEventListener("submit", pedirSaludo);
$("#btn-cargar").addEventListener("click", cargarMensajes);
