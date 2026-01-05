export default function (key: string, initialParas: { [x: string]: any }) {
    const filters = useState(key, () => (initialParas ?? {}))

    const setParam = (paramName: string | object, value: any) => {
        if (typeof paramName == 'object')
            filters.value = { ...filters.value, ...paramName }
        filters.value[paramName as string] = value
    }

    const filteredParams = computed(() => Object.fromEntries(
        Object.entries(filters.value)
            .filter(e => e[1])))

    const clearParam = (param: string) => setParam(param, null)

    return {
        params: filteredParams,
        setParam,
        clearParam,
        allParams: filters,
    }
}
