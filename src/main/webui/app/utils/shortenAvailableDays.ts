export default function shortenAvailableDays(availableDays: string[]): string | string[] {
    const dayConfig: { [key: string]: { order: number, label: string } } = {
        MONDAY: { order: 1, label: "Lun" },
        TUESDAY: { order: 2, label: "Mar" },
        WEDNESDAY: { order: 3, label: "Mié" },
        THURSDAY: { order: 4, label: "Jue" },
        FRIDAY: { order: 5, label: "Vie" },
        SATURDAY: { order: 6, label: "Sáb" },
        SUNDAY: { order: 7, label: "Dom" },
    }

    const sortedDays = [...availableDays]
        .map(d => d.toUpperCase())
        .filter(d => dayConfig[d])
        .sort((a, b) => dayConfig[a]!.order - dayConfig[b]!.order)

    const count = sortedDays.length
    
    if (count === 7) return "Todos los días"

    if (count === 2 && sortedDays.includes("SATURDAY") && sortedDays.includes("SUNDAY")) {
        return "Fines de semana"
    }

    const hasWeekend = sortedDays.some(d => d === "SATURDAY" || d === "SUNDAY")
    if (count === 5 && !hasWeekend) {
        return "Entre semana"
    }

    return sortedDays.map(d => dayConfig[d]!.label).join(", ")
}