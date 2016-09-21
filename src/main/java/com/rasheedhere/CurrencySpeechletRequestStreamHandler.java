package com.rasheedhere;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.rasheedhere.currency.CurrencySpeechlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by khanr on 8/19/2016.
 */
public final class CurrencySpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.add("amzn1.ask.skill.563a8130-5023-4246-b83a-093b7d4c69a4");
    }

    public CurrencySpeechletRequestStreamHandler() {
        super(new CurrencySpeechlet(), supportedApplicationIds);
    }
}
