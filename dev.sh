#!/usr/bin/env bash
# dev.sh: arranque del sistema daw-arquitectura (en Windows, ejecutar con Git Bash)
# Uso: ./dev.sh <comando> [servicios...]
set -euo pipefail

uso() {
    cat <<'EOF'
uso: ./dev.sh <comando> [servicios...]

  up [servicios...]    levanta todo el sistema (o solo los indicados: nginx app db minio)
  down                 para y elimina los contenedores (los volúmenes de datos se conservan)
  build                reconstruye la imagen de la app (jar de Spring Boot)
  logs [servicio]      sigue los logs en vivo (Ctrl+C para salir)
  ps                   estado de los servicios y puertos publicados
  reiniciar            down + up
EOF
}

comando="${1:-}"
shift 2>/dev/null || true

case "$comando" in
    up)
        if [ $# -gt 0 ]; then
            docker compose up -d --build "$@"
        else
            docker compose up -d --build
        fi
        ;;
    down)
        docker compose down
        ;;
    build)
        docker compose build "${1:-app}"
        ;;
    logs)
        if [ $# -gt 0 ]; then
            docker compose logs -f "$@"
        else
            docker compose logs -f
        fi
        ;;
    ps)
        docker compose ps
        ;;
    reiniciar)
        docker compose down
        docker compose up -d --build
        ;;
    *)
        uso
        exit 1
        ;;
esac
