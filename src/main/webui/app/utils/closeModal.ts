export default function openModal(name: string) {
    (document.getElementById(name) as HTMLDialogElement)?.close()
}