export const customFetch = async <T>(url: string, options: RequestInit): Promise<T> => {
    const token = localStorage.getItem(Keys.TOKEN)
    if (token) {
        options.headers = {
            ...options.headers,
            Authorization: `Bearer ${token}`
        }
    }
    const response = await fetch(url, options)
    const body = [204, 205, 304].includes(response.status) ? null : await response.text()
    const data = body ? JSON.parse(body) : {}
    if (!response.ok) {
        return Promise.reject(data)
    }
    return data
}

export default customFetch
