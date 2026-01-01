export default function () {
    return {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem(Keys.TOKEN)
    }
}