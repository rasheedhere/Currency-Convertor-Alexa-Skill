package com.rasheedhere.currency;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

import java.util.List;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by khanr on 8/19/2016.
 */
public class CurrencySpeechlet implements Speechlet{
    private static final Logger log = LoggerFactory.getLogger(CurrencySpeechlet.class);

    public CurrencySpeechlet() {
        log.info("In currency speech let");
    }

    public void onSessionStarted(SessionStartedRequest sessionStartedRequest, Session session) throws SpeechletException {
        log.info("onSessionStarted requestId="+ sessionStartedRequest.getRequestId()+ ", sessionId=" + session.getSessionId());
    }

    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) throws SpeechletException {
        log.info("onLaunch requestId="+ launchRequest.getRequestId()+ ", sessionId=" + session.getSessionId());
        return getWelcomeMessage();
    }

    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {
        log.info("onIntent requestId="+ intentRequest.getRequestId()+ ", sessionId=" + session.getSessionId());
        Intent intent = intentRequest.getIntent();
        //intentRequest.
        //intent.getSlot("country").
        String intentName= intent!=null ? intent.getName() : null;
        if("GetCurrency".equals(intentName)) {
            return getCurrencyResponse(intent.getSlot("country").getValue());
        } else if("HelloWorld".equals(intentName)) {
            return getWelcomeMessage();
        } else if("ConvertCurrency".equals(intentName)) {
            return convertCurrency(intent.getSlot("amount").getValue(), intent.getSlot("currencyFrom").getValue(), intent.getSlot("currencyTo").getValue());
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {
        log.info("onSessionEnded requestId="+ sessionEndedRequest.getRequestId()+ ", sessionId=" + session.getSessionId());
    }

    private SpeechletResponse convertCurrency(String amount, String currencyFrom, String currencyTo) {
        int amount_int = Integer.parseInt(amount);
        String speechText = amount + " "+ currencyFrom + " is ";
        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider("ECB");
        speechText += " "+ exchangeRateProvider.getExchangeRate("USD", "INR").getFactor().doubleValue() * amount_int + " " + currencyTo;
        SimpleCard card = new SimpleCard();
        card.setTitle("kaka");
        card.setContent(speechText);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    private SpeechletResponse getWelcomeMessage() {
        String speechText = "Welcome to kaka, a currency convertor. you can say what's the currency in India";

        SimpleCard card = new SimpleCard();
        card.setTitle("kaka");
        card.setContent(speechText);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    private SpeechletResponse getCurrencyResponse(String country) {
        String speechText = "Currency in "+ country + "is ";
        if("India".equalsIgnoreCase(country)) {
            speechText += "Indian Rupee";
        } else if("US".equalsIgnoreCase(country)) {
            speechText += "US Dollar";
        } else if("China".equalsIgnoreCase(country)){
            speechText += "Renminbi";
        } else {
            speechText += "could not find";
        }
        SimpleCard card = new SimpleCard();
        card.setTitle("kaka");
        card.setContent(speechText);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }
}
