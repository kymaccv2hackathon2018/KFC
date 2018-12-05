/* eslint-disable  func-names */
/* eslint-disable  no-console */

const Alexa = require('ask-sdk');
const request = require('sync-request');

const GetOrderStatusHandler = {
  canHandle(handlerInput) {
    const request = handlerInput.requestEnvelope.request;
    return request.type === 'LaunchRequest'
      || (request.type === 'IntentRequest'
        && request.intent.name === 'OrderStatusIntent');
  },
  handle(handlerInput) {
    const orderId = handlerInput.requestEnvelope.request.intent.slots.orderId.value;

    var orderStatus = {id: "-1", status: "Unknown"}
    if (orderId != null) {
      orderStatus = this.getOrderWithId(orderId);
    } else {
      orderStatus = this.getLatestOrder()
    }
  
    const speechOutput = "Order " + orderStatus.id + " is " + orderStatus.status;
    console.log("speechOutput: "+ speechOutput);

    return handlerInput.responseBuilder
      .speak(speechOutput)
      .withSimpleCard(SKILL_NAME, speechOutput)
      .getResponse();
  },
  getOrderWithId(id) {
    const url = "https://order-service.sa-hackathon-07.cluster.extend.sap.cx/orders/" + id + "/status";  
    console.log("url:" + url);
    const response = request('GET', url);  
    return JSON.parse(response.getBody());
  },
  getLatestOrder() {
    const url = "https://latest-order-status.sa-hackathon-07.cluster.extend.sap.cx/";
    console.log("url: " + url);
    const response = request('GET', url);
    console.log("responseBody: " + response.getBody());
    return JSON.parse(response.getBody());
  }
};

const HelpHandler = {
  canHandle(handlerInput) {
    const request = handlerInput.requestEnvelope.request;
    return request.type === 'IntentRequest'
      && request.intent.name === 'AMAZON.HelpIntent';
  },
  handle(handlerInput) {
    return handlerInput.responseBuilder
      .speak(HELP_MESSAGE)
      .reprompt(HELP_REPROMPT)
      .getResponse();
  }
};

const ExitHandler = {
  canHandle(handlerInput) {
    const request = handlerInput.requestEnvelope.request;
    return request.type === 'IntentRequest'
      && (request.intent.name === 'AMAZON.CancelIntent'
        || request.intent.name === 'AMAZON.StopIntent');
  },
  handle(handlerInput) {
    return handlerInput.responseBuilder
      .speak(STOP_MESSAGE)
      .getResponse();
  }
};

const SessionEndedRequestHandler = {
  canHandle(handlerInput) {
    const request = handlerInput.requestEnvelope.request;
    return request.type === 'SessionEndedRequest';
  },
  handle(handlerInput) {
    console.log(`Session ended with reason: ${handlerInput.requestEnvelope.request.reason}`);

    return handlerInput.responseBuilder.getResponse();
  }
};

const ErrorHandler = {
  canHandle(handlerInput, error) {
    return true;
  },
  handle(handlerInput, error) {
    console.log(`Error handled: ${error.message}`);
    return handlerInput.responseBuilder
      .speak('Order is not found')
      .getResponse();
  }
};

const SKILL_NAME = 'store';
const HELP_MESSAGE = 'You can say what\'s the status for order 1234, or, you can say exit... What can I help you with?';
const HELP_REPROMPT = 'What can I help you with?';
const STOP_MESSAGE = 'Goodbye!';

const skillBuilder = Alexa.SkillBuilders.standard();

exports.handler = skillBuilder
  .addRequestHandlers(
    GetOrderStatusHandler,
    HelpHandler,
    ExitHandler,
    SessionEndedRequestHandler
  )
  .addErrorHandlers(ErrorHandler)
  .lambda();