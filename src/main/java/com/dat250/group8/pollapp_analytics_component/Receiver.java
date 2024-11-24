package com.dat250.group8.pollapp_analytics_component;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

  private CountDownLatch latch = new CountDownLatch(1);
  private LinkedHashMap latestMessage;
  @Autowired
  private AggregatedPollRepository pollRepository;

//    public void receiveMessage(Map<String, Object> message) {
//      String msg = (String) message.get("message");
//    System.out.println("Received <" + msg + ">");
//    latch.countDown();
//  }

  public void receiveMessage(LinkedHashMap message) {
    //String decoded_string = new String(message, StandardCharsets.UTF_8);

    if(message.isEmpty()) {
      System.err.println("Received a null or empty message, skipping processing.");
      latch.countDown();
      return;
    }
    latestMessage = message;
    System.out.println("Received <" + latestMessage + ">");

    pollRepository.saveAggregatedPoll(latestMessage, "aggregatedPoll");
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

  public LinkedHashMap getLatestMessage() {
    return latestMessage;
  }
}