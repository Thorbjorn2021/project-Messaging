package com.dat250.group8.pollapp_analytics_component;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AggregatedPollRepository implements IAggregatedPollRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveAggregatedPoll(Map<String, Object> pollData, String collection) {
        try {
            mongoTemplate.save(pollData, "aggregatedPoll");
        } catch (Exception e) {
            System.err.println("Failed to save document: " + e.getMessage());
        }
    }
}
