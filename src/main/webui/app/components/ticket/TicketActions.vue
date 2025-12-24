<script setup>
import { AccountRole } from '~/models';
import { putApiTicketsIdOrder } from '~/services/ticket-resource/ticket-resource';

const toast = useToast()

const ticket = useState('ticket')

const order = () => putApiTicketsIdOrder(ticket.value.id)
    .then(_ => toast.success({ title: 'Productos ordenados con éxito' }))
    .then(_ => {
        switch (getCurrentUserRole()) {
            case AccountRole.ADMIN:
                return navigateTo('/admin')
            case AccountRole.CASHIER:
                return navigateTo('/cashier')
        }
    })

</script>

<template>
    <div class="grid grid-cols-2 gap-1">
        <button class="btn btn-accent">Pagar</button>
        <button class="btn">4</button>
        <button class="btn btn-error">Cancelar</button>
        <button class="btn btn-primary" @click="order">Ordenar</button>
    </div>
</template>