 import React from 'react';

const Notification = ({notificationMessage}) => {
    if (notification) {
        if (Notification) {
            if (Notification.permission === "granted") {
                var notification = new Notification(notificationMessage.title, { text: notificationMessage.message } );
            } else {
                console.log("Notification is denied");
            }
        } else {
            console.log("Notification not supported by browser");
        }
    }

    return null;
};

export default Notification;
