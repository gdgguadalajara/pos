export default defineNuxtPlugin((nuxtApp) => {
    nuxtApp.hook('app:mounted', () => {
        const description = "Punto de Venta open source, gratuito y de arquitectura cloud-native. Una solución moderna, ligera y flexible diseñada para cualquier tipo de negocio, con un enfoque total en la eficiencia, la escalabilidad y la libertad del código abierto."
        const welcome = "¡Bienvenido al sistema! Si eres desarrollador, únete a la comunidad en GitHub."
        const repository = "Repositorio: https://github.com/gdgguadalajara/pos"
        fetch("/favicon.ans")
            .then(res => res.text())
            .then(console.log)
            .then(_ => console.log(description))
            .then(_ => console.log(welcome))
            .then(_ => console.log(repository))
    });
});