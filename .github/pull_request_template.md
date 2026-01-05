# 🚀 Descripción del Cambio

## 📝 Resumen
## 🛠️ Tipo de Cambio
- [ ] ✨ **Feat**: Nueva funcionalidad.
- [ ] 🐛 **Fix**: Corrección de un error.
- [ ] 🧹 **Chore**: Tareas de mantenimiento (dependencias, configuración).
- [ ] 📖 **Doc**: Cambios en la documentación.
- [ ] 🧪 **Test**: Adición o corrección de pruebas.

---

## 💾 Cambios en Persistencia
- [ ] ¿Se agregaron nuevos scripts de **Flyway**?
- [ ] ¿Los cambios son compatibles tanto con **H2** (Standalone) como con **PostgreSQL**?
- [ ] ¿Se requiere limpiar la base de datos local para probar?

## ⚙️ Configuración y DevOps
- [ ] ¿Hay nuevas **variables de entorno** necesarias?
- [ ] ¿Se modificaron los **Dockerfiles** o los scripts de build (`build-all.sh`)?
- [ ] ¿Se requiere actualizar el volumen local `/data`?

---

## 🧪 ¿Cómo probar estos cambios?
1. **Perfil Standalone**:
```bash
   podman run ... (tu comando aquí)
```

2. **Endpoint a probar**: `GET /api/v1/...`
3. **Resultado esperado**: ...

---

## 📸 Evidencia (Opcional)

---

## ✅ Checklist antes de solicitar revisión

* [ ] Mi código sigue las guías de estilo del proyecto.
* [ ] He actualizado la documentación (README/Changelog) si es necesario.
* [ ] He verificado el build nativo (si aplica).
* [ ] Los nombres de las variables y métodos son descriptivos.

---