<script setup>
import { AccountRole } from '~/models';
import { deleteApiTicketsId, putApiTicketsIdOrder, putApiTicketsIdPay } from '~/services/ticket-resource/ticket-resource';

const toast = useToast()

const ticket = useState('ticket')

const order = () => putApiTicketsIdOrder(ticket.value.id)
    .then(_ => toast.success({ title: 'Productos ordenados con éxito' }))
    .then(_ => {
        switch (getCurrentUserRole()) {
            case AccountRole.ADMIN:
                return navigateTo('/admin')
            case AccountRole.CASHIER:
                return navigateTo('/cashier/tickets')
        }
    })

const pay = () => putApiTicketsIdPay(ticket.value.id)
    .then(_ => toast.success({ title: 'Ticket pagado con éxito' }))
    .then(_ => navigateTo('/cashier/tickets'))

const cancel = () => deleteApiTicketsId(ticket.value.id)
    .then(_ => toast.success({ title: 'Ticket cancelado con éxito' }))
    .then(_ => navigateTo('/cashier/tickets'))
</script>

<template>
    <div class="grid grid-cols-2 gap-1">
        <button class="btn btn-accent" @click="pay">Pagar</button>
        <div></div>
        <button class="btn btn-error" :disabled="!!ticket.items.length" @click="cancel">Cancelar</button>
        <button class="btn btn-primary" @click="order">Ordenar</button>
    </div>
</template>