export default function () {
    const user = localStorage.getItem(Keys.USER)
    if (!user) return null
    return JSON.parse(user)
}