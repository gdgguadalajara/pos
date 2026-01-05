<script setup>
import { PaymentMethod } from '~/models';
import { getApiTicketsIdPayments } from '~/services/ticket-resource/ticket-resource';

const { ticketId } = defineProps(['ticketId'])

const { data: payments } = useAsyncData(`getApiTicketPayments-${ticketId}`,
    () => getApiTicketsIdPayments(ticketId), { default: () => [] })

const methodToEs = (payment) => {
    switch (payment.method) {
        case PaymentMethod.CASH: return 'Efectivo'
        case PaymentMethod.CREDIT_CARD: return 'Tarjeta de credito'
        case PaymentMethod.DEBIT_CARD: return 'Tarjeta de debito'
        case PaymentMethod.TRANSFER: return 'Transferencia'
        case PaymentMethod.OTHER: return 'Otro'
        default: return payment.method
    }
}
</script>

<template>
    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
        <table class="table table-zebra">
            <thead class="bg-base-200">
                <tr>
                    <th>#</th>
                    <th>Monto</th>
                    <th>Metodo</th>
                    <th>Referencia externa</th>
                    <th>Cambio</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="payment in payments" :key="payment.id">
                    <td>
                        <button class="btn btn-link" @click="copyId(payment.id)">
                            {{ payment.id.slice(0, 8) }}...
                        </button>
                    </td>
                    <td>${{ payment.amount }}</td>
                    <td>{{ methodToEs(payment) }}</td>
                    <td>{{ payment.externalReference }}</td>
                    <td>${{ payment.changeGiven }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
