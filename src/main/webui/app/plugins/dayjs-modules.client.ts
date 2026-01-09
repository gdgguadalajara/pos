import relativeTime from "dayjs/plugin/relativeTime"
import isBetween from "dayjs/plugin/isBetween"
import dayjs from "dayjs"
import 'dayjs/locale/es'

export default defineNuxtPlugin((nuxtApp) => {
    nuxtApp.hook('app:mounted', () => {
        dayjs.extend(relativeTime)
        dayjs.extend(isBetween)
        dayjs.locale('es')
    })
})