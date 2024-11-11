package com.dat250.group8.pollapp_analytics_component;

import org.bson.Document;

import java.util.Map;

public interface IAggregatedPollRepository {
    void saveAggregatedPoll(Map<String, Object> pollData, String collection);
}
