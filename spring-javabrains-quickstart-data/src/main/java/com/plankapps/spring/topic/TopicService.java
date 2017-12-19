package com.plankapps.spring.topic;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {
    private List<Topic> topics = new ArrayList<Topic>(Arrays.asList(
            new Topic("java", "Java Course", "Great Java Course"),
            new Topic("spring", "Spring Course", "Great Spring Course"),
            new Topic("javascript", "Javascript Course", "Great Javascript Course")

    ));

    public List<Topic> getAllTopics() {
        return topics;
    }

    public Topic getTopic(String id) {
        return topics.stream().filter(topic -> topic.getId().equals(id)).findFirst().get();
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public void updateTopic(String id, Topic topic) {
        for(int i = 0; i < topics.size(); i++) {
            Topic currentTopic = topics.get(i);
            if(currentTopic.getId().equals(id)) {
                topics.set(i, topic);
                return;
            }
        }
        /*
        This seems to get a copy of topic from topics list and does not update the reference.
        Topic topicToUpdate = topics.stream().filter(topicFilter -> topicFilter.getId().equals(id)).findFirst().get();
        if(topicToUpdate != null) {
            topicToUpdate = topic;
        }*/


    }

    public void deleteTopic(String id) {
        topics.removeIf(t -> t.getId().equals(id));


        //without lambda:
       /* for(int i = 0; i < topics.size(); i++) {
            Topic currentTopic = topics.get(i);
            if(currentTopic.getId().equals(id)) {
                topics.remove(i);
            }
        }*/


        //implement equals and hascode in topic to make foreach work:
        /*for(Topic topic : topics) {
            if(topic.getId().equals(id)) {
                topics.remove(topic);
            }
        }*/
    }
}
