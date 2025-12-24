export default function getCurrentUserRole() {
    const user = JSON.parse(localStorage.getItem(Keys.USER) ?? '{}')
    return user?.account?.role
}