import { spawnNotification } from "./spawnnotification.js";
import React from 'react';

const Notification = ({notificationMessage}) => {
    if (notificationMessage) {
        if (Notification) {
            spawnNotification(notificationMessage);
        } else {
            console.log("Notification not supported by browser");
        }
    }

    return null;
};

export default Notification;
