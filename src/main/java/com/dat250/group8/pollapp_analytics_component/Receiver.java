package com.dat250.group8.pollapp_analytics_component;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

  private CountDownLatch latch = new CountDownLatch(1);
  private Map<String, Object> latestMessage;
  @Autowired
  private AggregatedPollRepository pollRepository;

//    public void receiveMessage(Map<String, Object> message) {
//      String msg = (String) message.get("message");
//    System.out.println("Received <" + msg + ">");
//    latch.countDown();
//  }

  public void receiveMessage(Map<String, Object> message) {

    if(message == null) {
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

  public Map<String, Object> getLatestMessage() {
    return latestMessage;
  }
}