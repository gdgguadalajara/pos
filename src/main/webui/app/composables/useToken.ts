export default function () {
    return useState('session.token', () => {
        const token = localStorage.getItem(Keys.TOKEN)
        if (!token) return null
        return token
    })
}
