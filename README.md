<h1><a href="https://gdgguadalajara.com"><img align="center" height="50" src="src/main/webui/public/favicon.svg"></a> Point of Sale</h1>

[![License: AGPL v3](https://img.shields.io/badge/License-AGPL_v3-blue.svg)](LICENSE)
[![Docker Image](https://img.shields.io/badge/docker-ghcr.io-blue?logo=docker&logoColor=white)](https://github.com/gdgguadalajara/pos/pkgs/container/pos)
[![Platform](https://img.shields.io/badge/platform-linux-lightgrey)](https://www.linux.org/)

> [!IMPORTANT]
> **Aviso:** Este proyecto se encuentra en desarrollo y no se recomienda su uso para producción.

Bienvenido a **POS (Point of Sale)**, un sistema de Punto de Venta **gratuito, libre y Cloud Native**, desarrollado por la comunidad del **Google Developer Group (GDG) Guadalajara**.

Nuestro objetivo es ofrecer una solución de software robusta, ligera y fácil de desplegar para pequeños negocios, manteniendo la filosofía del **Software Libre**.

## 🚀 Stack Tecnológico

El proyecto está diseñado como un monolito moderno optimizado para la nube, asegurando bajo consumo de recursos y alto rendimiento.

* **Backend:** Java 21 + Quarkus.
* **Frontend:** Nuxt (Vue.js) servido mediante Quarkus Quinoa.
* **Base de Datos:** SQLite (Embebida).
* **Estilos:** Tailwind CSS + **daisyUI** (Componentes listos para usar).

## 🐳 Despliegue con Docker (Linux)

### 1. Preparación de seguridad
Antes de iniciar, debes generar tus propias llaves para la firma de tokens. Puedes usar nuestro script o generarlas manualmente. Asegúrate de que estén en una carpeta llamada `pos_data`:

```bash
./scripts/create-jwt-pem.sh # Esto creará las llaves en ./data/
mv data pos_data             # Renombramos para el volumen de Docker
```

### 2. Ejecutar el sistema

```bash
docker run -d \
  --name pos-gdg \
  -p 8080:8080 \
  -v $(pwd)/pos_data:/work/data \
  ghcr.io/gdgguadalajara/pos:latest
```

* **`/work/data`**: Ahora contiene tanto `gdgguadalajara_pos.sqlite3` como `privateKey.pem` y `publicKey.pem`. Todo lo sensible está bajo tu control fuera del contenedor.

### 3. Primeros Pasos

Una vez que el contenedor esté corriendo, abre tu navegador en `http://localhost:8080`.

#### Credenciales por defecto

* **Usuario:** `admin@gdgguadalajara.com`
* **Contraseña:** `admin`

## ⚙️ Configuración Avanzada

El sistema viene preconfigurado para funcionar inmediatamente. Sin embargo, puedes personalizar el comportamiento utilizando **Variables de Entorno** al ejecutar el contenedor.

Las variables más comunes para entornos de producción son:

| Variable de Entorno           | Propiedad Equivalente         | Descripción                       | Valor por Defecto                             |
| ----------------------------- | ----------------------------- | --------------------------------- | --------------------------------------------- |
| `QUARKUS_HTTP_PORT`           | `quarkus.http.port`           | Puerto donde escucha el servidor. | `8080`                                        |
| `QUARKUS_DATASOURCE_JDBC_URL` | `quarkus.datasource.jdbc.url` | Ruta de conexión a la BD.         | `jdbc:sqlite:data/gdgguadalajara_pos.sqlite3` |
| `QUARKUS_LOG_LEVEL`           | `quarkus.log.level`           | Nivel de detalle de los logs.     | `INFO`                                        |

**Ejemplo: Cambiar el puerto a 9090:**

```bash
docker run -d -p 9090:9090 -e QUARKUS_HTTP_PORT=9090 ...

```
ℹ️ Más información: Para ver la configuración base completa y descubrir otras propiedades que puedes sobrescribir, consulta el archivo de configuración original: 📄 [application.properties](src/main/resources/application.properties)

## 🛠 Desarrollo Local

Si deseas contribuir al código o compilarlo tú mismo, necesitarás tener instalado **Java JDK 21**.

1. **Clona el repositorio:**
```bash
git clone https://github.com/gdgguadalajara/pos.git
cd pos
```


2. **Genera los certificados de seguridad:**
```bash
./scripts/create-jwt-pem.sh
```


3. **Ejecuta en modo desarrollo (Live Coding):**
```bash
./gradlew quarkusDev -Dvertx.disableURIValidation=true
```



Para más detalles sobre el entorno de desarrollo y la arquitectura, consulta nuestra **[Guía de Contribución](CONTRIBUTING.md)**.

## 🤝 Cómo Contribuir

¡Las contribuciones son bienvenidas!

1. Lee nuestro **[Código de Conducta](CODE_OF_CONDUCT.md)** para asegurar un ambiente saludable.
2. Revisa la **[Guía de Contribución](CONTRIBUTING.md)** para entender el flujo de trabajo y los estándares.

## 📜 Licencia

Este proyecto es **Software Libre** bajo la licencia **GNU Affero General Public License v3.0 (AGPL-3.0)**.

> Tienes la libertad de usar, estudiar, compartir y modificar el software. Si modificas este software y lo ofreces como un servicio a través de una red (SaaS), **estás obligado a compartir el código fuente completo** de tu versión modificada bajo la misma licencia.

Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

Hecho con ❤️ por el **GDG Guadalajara**.
