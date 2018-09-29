export function createNotification(notificationMessage) {
    if (Notification) {
        if (Notification.permission === "granted") {
            var notification = new Notification(notificationMessage);
        } else {
            console.log("Notification is denied");
        }
    } else {
        console.log("Notification not supported by browser");
    }
}
