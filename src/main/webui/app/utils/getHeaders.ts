export default function getHeaders() {
    return {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem(Keys.TOKEN)
    }
}