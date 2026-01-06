/**
 * 
 * @param query - example: (min-width: 700px)
 */
export default function (query: string) {
    const mediaQueryList = window.matchMedia(query);
    const matches = ref(mediaQueryList.matches)
    const handleScreenChange = (event: MediaQueryListEvent) => matches.value = event.matches
    mediaQueryList.addEventListener("change", handleScreenChange, { passive: true })
    return computed(() => matches.value);
}