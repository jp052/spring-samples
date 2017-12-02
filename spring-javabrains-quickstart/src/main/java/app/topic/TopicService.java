package app.topic;

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
}
