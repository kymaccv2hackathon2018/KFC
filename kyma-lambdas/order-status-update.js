const request = require('request');
const util = require('util')
const traceHeaders = ['x-request-id', 'x-b3-traceid', 'x-b3-spanid', 'x-b3-parentspanid', 'x-b3-sampled', 'x-b3-Flags', 'x-ot-span-context']
const orderstatusmap = { 
    'order.created': 'submitted' ,
    'order.deliverysent':  'shipped' ,
    'order.completed' :  'delivered'
}

// var statusmap = new Map([['order.created', 'submitted'],['order.deliverysent', 'shipped'], ['order.completed',  'delivered']]);


module.exports = { main: function (event, context) {
    console.log("*************************************Entering Event Lambda*************************************");
    console.log("In Lambda with event.data.orderCode: "+event.data.orderCode);
    var orderId = event.data.orderCode;

    // Pass the headers through to the next calls, so that tracing will work
    var traceCtxHeaders = extractTraceHeaders(event.extensions.request.headers)
    console.log( "In Lambda with order status: "+orderstatusmap[event.extensions.request.headers['kyma-event-type']]);

    // Construct the order data that we want to persist
    var order = {
        id: orderId,
        status: orderstatusmap[event.extensions.request.headers['kyma-event-type']]
    }

    // Call our orders-cloudlab4 service to persist the data
    var orderServiceUrl = "https://order-service.sa-hackathon-07.cluster.extend.sap.cx/orders/"
    console.log('Order service Url is ' + orderServiceUrl)
    request.post({headers: traceCtxHeaders, url: orderServiceUrl,json: order}, function(error, response, body){
        console.log("In Lambda with response.statusCode: "+response.statusCode)
    })
    console.log("-----------------------------------Lambda END-----------------------------------");
}}
  
  
function extractTraceHeaders(headers) {
    // Used to pass the headers through to the next calls, so that tracing will work
    console.log(headers)
    var map = {};
    for (var i in traceHeaders) {
        h = traceHeaders[i]
        headerVal = headers[h]
        console.log('header' + h + "-" + headerVal)
        if (headerVal !== undefined) {
            console.log('if not undefined header' + h + "-" + headerVal)
            map[h] = headerVal
        }
    }
    return map;
}