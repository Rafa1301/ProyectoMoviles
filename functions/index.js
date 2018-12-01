const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.sendPush = functions.database.ref('/Usuarios').onCreate((snap, context) =>  {

        let tokens = [];
    tokens.push("e1KD26v-D3s:APA91bF74xZ8xNzkCzod90-ziuKQlYWbMA0O4YLxNtBm8LM54O2CrOoTdar-rR3sbIQsKmk8cxJeCaCx5WUZxvQr27DUphaaDSfZYU4cPbcyPzhFzXiVt1lIpt18mf6pqbL2dsNGaov9");
    tokens.push("dQBmxzs-6dk:APA91bFrxRae12xJb1J6oOTIOxsvjvB_cY5DMwR5mAA-ReQOSzmYVrDidn-VAqO5IpxPPnHdgzK-AYsWKYW1YSe5WOFTUDaaHUuMIwrrkMNHYEF1twyPjX6HOsSz11yyPFKwuC61CN91");
        /*for (let user of users) {
            if (user.admin !== 0) {
                console.log('User', 'User: ' + user);
                console.log('User token', 'User: ' + user.device_id);

                tokens.push(user.device_id);
            }
        }*/
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