var AWS = require('aws-sdk')

AWS.config.update({region: 'us-east-1'});

module.exports = { main: async function (event, context) {
    
    console.log(JSON.stringify(event.data))
    
    let phoneNumber = '+15184799686'
    if (event.data.phoneNumber) {
        phoneNumber = event.data.phoneNumber
    }
    
    // Create publish parameters
    var params = {
      Message: 'The status of your order ' + event.data.id + ' from kyma store is now "' + event.data.status + '"', /* required */
      PhoneNumber: phoneNumber,
    };

    // Create promise and SNS service object
    var publishTextPromise =  await new AWS.SNS({apiVersion: '2010-03-31'}).publish(params).promise();
    console.log("MessageID is " + publishTextPromise.MessageId);
} }