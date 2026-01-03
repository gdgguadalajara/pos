<script setup>
import { AccountRole, PaymentMethod, TicketStatus } from '~/models';
import { postApiPaymentsTicketId } from '~/services/payment-resource/payment-resource';
import { deleteApiTicketsId, putApiTicketsIdOrder } from '~/services/ticket-resource/ticket-resource';

const toast = useToast()

const ticket = useState('ticket')

const changeGiven = ref(0)

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

const pay = (e) => {
    const amount = +e.target.amount.value
    const method = e.target.payment_method.value
    if (amount < ticket.value.totalAmount)
        return toast.error({ title: 'El monto ingresado es menor al total del ticket' })
    postApiPaymentsTicketId(ticket.value.id, { amount, method })
        .then(payment => {
            if (method === PaymentMethod.CASH && payment.ticket.status == TicketStatus.PAID && payment.changeGiven > 0) {
                changeGiven.value = payment.changeGiven
                closeModal('pay_ticket_modal')
                openModal('change_given_modal')
                return toast.success({ title: 'Ticket pagado con éxito' })
            }
            if (payment.ticket.status == TicketStatus.PAID) {
                toast.success({ title: 'Ticket pagado con éxito' })
                return navigateTo('/cashier/tickets')
            }
            toast.success({ title: 'Pago registrado con exito' })
        })
        .catch(err => toast.error({ title: err.message }))
}

const cancel = () => deleteApiTicketsId(ticket.value.id)
    .then(_ => toast.success({ title: 'Ticket cancelado con éxito' }))
    .then(_ => navigateTo('/cashier/tickets'))
</script>

<template>
    <div class="grid grid-cols-2 gap-1">
        <button class="btn btn-info" @click="openModal('pay_ticket_modal')" :disabled="!ticket.items.length">
            Pagar
        </button>
        <dialog id="pay_ticket_modal" class="modal">
            <div class="modal-box">
                <h3 class="text-lg font-bold">Pagar ticket</h3>
                <form id="pay_ticket_form" class="flex flex-col gap-1" @submit.prevent="pay">
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Metodo de pago</legend>
                        <select class="select w-full" name="payment_method">
                            <option :value="PaymentMethod.CASH">Efectivo</option>
                            <option :value="PaymentMethod.CREDIT_CARD">Tarjeta de Credito</option>
                            <option :value="PaymentMethod.DEBIT_CARD">Tarjeta de debito</option>
                            <option :value="PaymentMethod.TRANSFER">Transferencia</option>
                            <option :value="PaymentMethod.OTHER">Otro</option>
                        </select>
                    </fieldset>
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Monto</legend>
                        <input type="number" class="input w-full" name="amount" />
                    </fieldset>
                </form>
                <div class="modal-action">
                    <form method="dialog">
                        <button class="btn">Cerrar</button>
                    </form>
                    <button type="submit" class="btn btn-primary" form="pay_ticket_form">
                        Pagar
                    </button>
                </div>
            </div>
            <form method="dialog" class="modal-backdrop">
                <button>close</button>
            </form>
        </dialog>
        <div></div>
        <button class="btn btn-error" :disabled="!!ticket.items.length" @click="cancel">Cancelar</button>
        <button class="btn btn-primary" @click="order">Ordenar</button>
    </div>
    <dialog id="change_given_modal" class="modal">
        <div class="modal-box">
            <h3 class="text-lg font-bold">Cambio</h3>
            <div class="text-6xl grid place-content-center">
                ${{ changeGiven }}
            </div>
            <div class="modal-action">
                <form method="dialog" @submit.prevent="() => navigateTo('/cashier/tickets')">
                    <button class="btn">Cerrar</button>
                </form>
            </div>
        </div>
        <form method="dialog" class="modal-backdrop" @submit.prevent="() => navigateTo('/cashier/tickets')">
            <button>close</button>
        </form>
    </dialog>
</template>