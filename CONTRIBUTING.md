# Guía de Contribución

¡Gracias por tu interés en contribuir al proyecto del **Google Developer Group (GDG) Guadalajara**! 🎉

Este proyecto es Open Source y valoramos enormemente el tiempo y esfuerzo que dedicas para ayudarnos a mejorar. Ya sea corrigiendo errores, mejorando la documentación o proponiendo nuevas funcionalidades, toda ayuda es bienvenida.

## Código de Conducta

Este proyecto y todos sus participantes se rigen por nuestro [Código de Conducta](CODE_OF_CONDUCT.md). Al participar, se espera que defiendas este código. Por favor, reporta cualquier comportamiento inaceptable al equipo del proyecto.

## Stack Tecnológico

Para que te sientas cómodo navegando por el código, aquí está lo que usamos:

* **Backend:** Java 21 + Quarkus.
* **Frontend:** Nuxt (Vue.js) servido mediante la extensión Quarkus Quinoa.
* **Base de Datos:** SQLite3 (Embebida, no requiere configuración externa).
* **Frontend:** Utilizamos **Tailwind CSS** junto con **daisyUI**. Antes de crear un componente desde cero, por favor revisa la [documentación de daisyUI](https://daisyui.com/components/) para utilizar las clases de componentes existentes (ej. `btn`, `card`, `modal`). Esto mantiene la consistencia visual del Punto de Venta.

## Pre-requisitos

Antes de empezar, asegúrate de tener instalado en tu entorno local:

1. **Java JDK 21** (Es mandatorio para esta versión de Quarkus).
2. **Git**.

> **Nota sobre Node.js:** Gradle se encargará de gestionar las dependencias del frontend gracias a Quinoa, pero tener Node instalado localmente puede ser útil para herramientas de desarrollo.

## Configuración del Entorno (Quick Start)

Sigue estos pasos para tener el proyecto corriendo en tu máquina local:

### 1. Clonar el repositorio

```bash
git clone https://github.com/gdgguadalajara/pos.git
cd pos

```

### 2. Generar Certificados JWT

Utilizamos `SmallRye JWT` para la autenticación, lo cual requiere un par de llaves RSA (pública y privada). Hemos incluido un script para generarlas automáticamente.

Ejecuta lo siguiente desde la raíz del proyecto:

```bash
# Dar permisos de ejecución (si es necesario)
chmod +x ./scripts/create-jwt-pem.sh

# Ejecutar el script
./scripts/create-jwt-pem.sh

```

Esto generará los archivos `.pem` necesarios en `src/main/resources/`.

### 3. Ejecutar la Aplicación

Para levantar tanto el backend (Quarkus) como el frontend (Nuxt/Quinoa) en modo desarrollo con recarga en caliente (Live Coding), usa el siguiente comando:

```bash
./gradlew quarkusDev -Dvertx.disableURIValidation=true

```

* **¿Por qué `-Dvertx.disableURIValidation=true`?** Este flag es necesario para evitar conflictos de validación de URIs durante el desarrollo con el proxy de frontend.
* **Base de Datos:** No necesitas levantar Docker ni instalar nada extra. La aplicación usará SQLite en modo embebido automáticamente.

Una vez inicie, podrás acceder a la aplicación generalmente en `http://localhost:8080`.

## Cómo Contribuir

1. **Haz un Fork** del repositorio.
2. Crea una rama para tu feature o bugfix:
`git checkout -b feature/mi-nueva-feature`
3. Realiza tus cambios y haz **commits** descriptivos.
4. Asegúrate de que el proyecto compila y corre correctamente.
5. Haz **Push** a tu rama:
`git push origin feature/mi-nueva-feature`
6. Abre un **Pull Request (PR)** hacia la rama `dev` de este repositorio.

## Estilo de Código

* Intentamos seguir las convenciones estándar de **Java** y **Quarkus**.
* Para el frontend, seguimos las buenas prácticas de **Vue 3** y **Nuxt**.

## ¿Necesitas ayuda?

Si tienes dudas sobre cómo correr el proyecto o encuentras un problema, no dudes en abrir un **Issue** en el repositorio etiquetado como `question` o contactar a los organizadores del GDG Guadalajara.

¡Gracias por codear con nosotros! 🚀
