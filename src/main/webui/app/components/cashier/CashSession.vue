<script setup>
import { getApiCashSessionsCurrent, getApiCashSessionsCurrentSummary, postApiCashSessionsOpen, putApiCashSessionsClose } from '~/services/cash-session-resource/cash-session-resource';

const toast = useToast()
const cashSessionState = useState('cashSession')

const {
    data: cashSession,
    status,
    refresh
} = useAsyncData('getApiCashSessionsCurrent', () => getApiCashSessionsCurrent())

const {
    data: summary,
    status: statusSummary,
    refresh: refreshSummary
} = useAsyncData('getApiCashSessionsSummary', () => getApiCashSessionsCurrentSummary(),
    { immediate: status.value == 'success' })

const openCashSession = (e) =>
    postApiCashSessionsOpen({ initialBalance: +e.target.initialBalance.value })
        .then(_ => e.target.reset())
        .then(_ => toast.success({ title: 'Caja abierta con éxito' }))
        .then(_ => refresh())
        .then(_ => closeModal('open_cash_session'))

const closeCashSession = (e) =>
    putApiCashSessionsClose({ reportedBalance: +e.target.finalBalance.value, note: e.target.note.value })
        .then(_ => e.target.reset())
        .then(_ => toast.success({ title: 'Caja cerrada con éxito' }))
        .then(_ => refresh())
        .then(_ => closeModal('close_cash_session'))

watch([status, cashSession], ([status, cashSession]) => {
    if (status == 'error')
        openModal('open_cash_session')
    if (status != 'success') return
    cashSessionState.value = cashSession
    refreshSummary()
})
</script>

<template>
    <div v-if="status == 'pending'" class="grid place-items-center">
        <span class="loading loading-ring loading-xl"></span>
    </div>
    <button v-if="status == 'error'" @click="openModal('open_cash_session')"
        class="inline-flex btn btn-primary btn-outline">
        Abrir Caja
    </button>
    <Teleport to="#teleports">
        <dialog id="open_cash_session" class="modal">
            <div class="modal-box">
                <h3 class="text-lg font-bold">Abrir caja</h3>
                <form id="open_cash_session_form" class="flex flex-col gap-1" @submit.prevent="openCashSession">
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Monto inicial</legend>
                        <input type="number" class="input w-full" name="initialBalance" />
                    </fieldset>
                </form>
                <div class="modal-action">
                    <form method="dialog">
                        <button class="btn">Cerrar</button>
                    </form>
                    <button type="submit" class="btn btn-primary" form="open_cash_session_form">
                        Abrir
                    </button>
                </div>
            </div>
        </dialog>
    </Teleport>
    <button v-if="status == 'success'" @click="openModal('close_cash_session')"
        class="inline-flex btn btn-warning btn-outline">
        Cerrar Caja
    </button>
    <Teleport to="#teleports">
        <dialog id="close_cash_session" class="modal">
            <div class="modal-box">
                <h3 class="text-lg font-bold">Cerrar caja</h3>
                <form v-if="statusSummary == 'success'" id="close_cash_session_form" class="grid grid-cols-2 gap-1"
                    @submit.prevent="closeCashSession">
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Ventas totales:</legend>
                        <div
                            class="h-10 w-full grid items-center px-3 border rounded border-base-content/20 shadow shadow-base-content/3">
                            ${{ summary.totalSales }}
                        </div>
                    </fieldset>
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Gastos totales:</legend>
                        <div
                            class="h-10 w-full grid items-center px-3 border rounded border-base-content/20 shadow shadow-base-content/3">
                            ${{ summary.totalExpenses }}
                        </div>
                    </fieldset>
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Balance inicial:</legend>
                        <div
                            class="h-10 w-full grid items-center px-3 border rounded border-base-content/20 shadow shadow-base-content/3">
                            ${{ summary.initialBalance }}
                        </div>
                    </fieldset>
                    <fieldset class="fieldset">
                        <legend class="fieldset-legend">Balance esperado:</legend>
                        <div
                            class="h-10 w-full grid items-center px-3 border rounded border-base-content/20 shadow shadow-base-content/3">
                            ${{ summary.expectedBalance }}
                        </div>
                    </fieldset>
                    <fieldset class="fieldset col-span-2">
                        <legend class="fieldset-legend">Balance final</legend>
                        <input type="number" class="input w-full" name="finalBalance" />
                    </fieldset>
                    <fieldset class="fieldset col-span-2">
                        <legend class="fieldset-legend">Notas</legend>
                        <textarea class="textarea h-24 w-full" name="note"></textarea>
                    </fieldset>
                </form>
                <div class="modal-action">
                    <form method="dialog">
                        <button class="btn">Cerrar</button>
                    </form>
                    <button type="submit" class="btn btn-primary" form="close_cash_session_form">
                        Cerrar caja
                    </button>
                </div>
            </div>
        </dialog>
    </Teleport>
</template>