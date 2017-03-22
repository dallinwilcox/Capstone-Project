//referenced
// https://firebase.googleblog.com/2016/08/sending-notifications-between-android.html
//https://github.com/firebase/functions-samples/blob/master/fcm-notifications/functions/index.js
// https://firebase.google.com/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// })
'use strict';
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

/**
 * Triggers when a device requests a notification.
 *
 * client app writes data to be sent to to `/notify/{token}/{data}`.
  */
exports.sendDataNotification = functions.database.ref('/notify/{token}/{data}').onWrite(event => {
  const token = event.params.token;
  const data = event.data.val();
  if (!data) {
    //message was removed from the database, so no-op
    console.log('data empty due to removal from DB, no-op')
    return;
  }
    // Notification details.
    const payload = {};
//    make sure all property types are strings and not numbers
    Object.keys(data).forEach((key) => {
      if (typeof data[key] === 'number') {
        data[key] = data[key].toString();
      }
    });
    payload.data = data;

  console.log('payload=', payload, 'for token:', token);

    // Send data to device represented by token
    return admin.messaging().sendToDevice(token,payload).then(response => {
      // For each message check if there was an error.
      const tokensToRemove = [];
      response.results.forEach((result, index) => {
        const error = result.error;
        if (error) {
          console.error('Failure sending notification to', tokens[index], error);
          // Cleanup the tokens which are not registered anymore.
          if (error.code === 'messaging/invalid-registration-token' ||
              error.code === 'messaging/registration-token-not-registered') {
            tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
          }
        }
      });
      return Promise.all(tokensToRemove);
    });
  });