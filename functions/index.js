const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.sendPush = functions.database.ref('/Usuarios').onWrite(event => {

    return LoadUser.then(users => {
        let tokens = [];
        for (let user of users) {
            if (user.admin != 0) {
                console.log('User', 'User: ' + user);
                console.log('User token', 'User: ' + user.device_id);

                tokens.push(user.device_id);
            }
        }
        let payload = {
            notification: {
                title: 'Usuario Registrado',
                body: 'Accede para habilitar al nuevo usuario',
                sound: 'default',
                badge: '1'
            }
        };
        return admin.messaging().sendToDevice(tokens, payload);
    });

    function LoadUser() {
        let dbRef = admin.database().ref('/Usuarios');
        let defer = new Promise((resolve, reject) => {
            dbRef.once('value', (snap) => {
                let data = snap.val();
                let users = [];
                for (var propiety in users) {
                    users.push(data[propiety]);
                }
                resolve(users);
            }, (err) => {
                reject(err);
            });
        });
        return defer;
    }

});

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
