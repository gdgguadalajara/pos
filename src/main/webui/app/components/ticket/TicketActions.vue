<script setup>
import QRCode from 'qrcode'
import { AccountRole, PaymentMethod, TicketItemStatus } from '~/models';
import { postApiCashSessionsIdTicketsTicketIdPayments } from '~/services/cash-session-ticket-payments-resource/cash-session-ticket-payments-resource';
import { deleteApiTicketsId, getApiTicketsIdQr, putApiTicketsIdOrder } from '~/services/ticket-resource/ticket-resource';

const { isQuicksale } = defineProps(['isQuicksale'])

const toast = useToast()

const ticket = useState('ticket')
const cashSession = useState('cashSession')
const session = useSession()
const role = session.value.account.role

const amount = useTemplateRef('amount')
const changeGiven = ref(0)
const isLoadingQR = ref()

const order = () => putApiTicketsIdOrder(ticket.value.id)
    .then(_ => toast.success({ title: 'Productos ordenados con éxito' }))
    .then(_ => navigateTo(`/${role}/tickets`))

const pay = (e) => {
    const amount = +e.target.amount.value
    const method = e.target.payment_method.value
    if (amount < ticket.value.totalAmount)
        return toast.error({ title: 'El monto ingresado es menor al total del ticket' })
    if (!cashSession.value)
        return Promise.resolve()
            .then(toast.error({ title: 'No hay una sesión de caja abierta' }))
            .then(_ => closeModal('pay_ticket_modal'))
    postApiCashSessionsIdTicketsTicketIdPayments(cashSession.value.id, ticket.value.id, { amount, method })
        .then(payment => changeGiven.value = payment.changeGiven)
        .then(_ => isLoadingQR.value = true)
        .then(_ => refreshNuxtData('getApiCashSessionsSummary'))
        .then(_ => getApiTicketsIdQr(ticket.value.id))
        .then(({ payload }) => QRCode.toCanvas(document.getElementById('pay-ticket-canvas'), payload, (err) => {
            if (err) return console.error(err)
            isLoadingQR.value = false
        }))
        .then(_ => closeModal('pay_ticket_modal'))
        .then(_ => openModal('change_given_modal'))
        .catch(err => toast.error({ title: err.message }))
}

const openTicketModal = () => getApiTicketsIdQr(ticket.value.id)
    .then(({ payload }) => Promise.resolve()
        .then(_ => isLoadingQR.value = true)
        .then(_ => openModal('waiter_ticket_modal'))
        .then(_ => QRCode.toCanvas(document.getElementById('waiter-ticket-canvas'), payload, (err) => {
            if (err) return console.error(err)
            isLoadingQR.value = false
        })))

const cancel = () => deleteApiTicketsId(ticket.value.id)
    .then(_ => toast.success({ title: 'Ticket cancelado con éxito' }))
    .then(_ => navigateTo(`/${role}/tickets`))

const numberBtn = (value) =>
    amount.value.value = amount.value.value + value

const clear = () => amount.value.value = ''

const remove = () => amount.value.value = amount.value.value.slice(0, -1)
</script>

<template>
    <div class="grid grid-cols-2 gap-1">
        <button v-if="role != AccountRole.WAITER" class="btn btn-info" :class="{ 'col-span-2': !isQuicksale }"
            @click="openModal('pay_ticket_modal')" :disabled="!ticket.items.length">
            Pagar
        </button>
        <button class="btn btn-error" :disabled="!!ticket.items.length" @click="cancel">
            Cancelar
        </button>
        <button v-if="role == AccountRole.WAITER" @click="openTicketModal" class="btn btn-info">
            Ticket
        </button>
        <button v-if="!isQuicksale" class="btn btn-primary" :class="{ 'col-span-2': role == AccountRole.WAITER }"
            :disabled="!ticket.items.some(i => i.status == TicketItemStatus.ADDED)" @click="order">
            Ordenar
        </button>
    </div>
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
                    <legend class="fieldset-legend">Total a pagar</legend>
                    <input readonly="true" type="text" class="input w-full" :value="'$' + ticket.totalAmount" />
                </fieldset>
                <fieldset class="fieldset">
                    <legend class="fieldset-legend">Monto</legend>
                    <input ref="amount" type="number" class="input w-full" name="amount" />
                </fieldset>
                <div class="flex flex-col">
                    <div class="flex flex-row w-full">
                        <button @click="numberBtn('1')" class="btn btn-xl flex-1" type="button">1</button>
                        <button @click="numberBtn('2')" class="btn btn-xl flex-1" type="button">2</button>
                        <button @click="numberBtn('3')" class="btn btn-xl flex-1" type="button">3</button>
                    </div>
                    <div class="flex flex-row w-full">
                        <button @click="numberBtn('4')" class="btn btn-xl flex-1" type="button">4</button>
                        <button @click="numberBtn('5')" class="btn btn-xl flex-1" type="button">5</button>
                        <button @click="numberBtn('6')" class="btn btn-xl flex-1" type="button">6</button>
                    </div>
                    <div class="flex flex-row w-full">
                        <button @click="numberBtn('7')" class="btn btn-xl flex-1" type="button">7</button>
                        <button @click="numberBtn('8')" class="btn btn-xl flex-1" type="button">8</button>
                        <button @click="numberBtn('9')" class="btn btn-xl flex-1" type="button">9</button>
                    </div>
                    <div class="flex flex-row w-full">
                        <button @click="clear" class="btn btn-xl flex-1" type="button">
                            <Icon name="material-symbols:cancel-outline" class="text-3xl" />
                        </button>
                        <button @click="numberBtn('0')" class="btn btn-xl flex-1" type="button">0</button>
                        <button @click="remove" class="btn btn-xl flex-1" type="button">
                            <Icon name="material-symbols:arrow-back-rounded" class="text-3xl" />
                        </button>
                    </div>
                </div>
            </form>
            <div class="modal-action">
                <form method="dialog" class="flex-1">
                    <button class="btn w-full">Cerrar</button>
                </form>
                <button type="submit" class="btn btn-primary flex-1" form="pay_ticket_form">
                    Pagar
                </button>
            </div>
        </div>
        <form method="dialog" class="modal-backdrop">
            <button>close</button>
        </form>
    </dialog>
    <dialog id="change_given_modal" class="modal">
        <div class="modal-box">
            <h3 class="text-lg font-bold">Ticket</h3>
            <div v-if="changeGiven > 0" class="text-6xl grid place-content-center mb-3">
                Cambio: ${{ changeGiven }}
            </div>
            <div v-if="isLoadingQR" class="grid place-items-center">
                <span class="loading loading-ring loading-xl"></span>
            </div>
            <div class="grid place-items-center">
                <canvas id="pay-ticket-canvas"></canvas>
            </div>
            <div class="modal-action">
                <form method="dialog" @submit.prevent="() => navigateTo(`/${role}/tickets`)">
                    <button class="btn">Cerrar</button>
                </form>
            </div>
        </div>
        <form method="dialog" class="modal-backdrop" @submit.prevent="() => navigateTo(`/${role}/tickets`)">
            <button>close</button>
        </form>
    </dialog>
    <dialog id="waiter_ticket_modal" class="modal">
        <div class="modal-box">
            <h3 class="text-lg font-bold">Ticket</h3>
            <div v-if="isLoadingQR" class="grid place-items-center">
                <span class="loading loading-ring loading-xl"></span>
            </div>
            <div class="grid place-items-center">
                <canvas id="waiter-ticket-canvas"></canvas>
            </div>
            <div class="modal-action">
                <form method="dialog">
                    <button class="btn">Cerrar</button>
                </form>
            </div>
        </div>
        <form method="dialog" class="modal-backdrop">
            <button>close</button>
        </form>
    </dialog>
</template>