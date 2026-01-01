export default function () {
    const user = JSON.parse(localStorage.getItem(Keys.USER) ?? '{}')
    return user?.account?.role
}