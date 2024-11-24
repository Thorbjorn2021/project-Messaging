package com.dat250.group8.pollapp_analytics_component;


import java.util.Map;

public interface IAggregatedPollRepository<T> {
    void saveAggregatedPoll(T pollData, String collection);
}
