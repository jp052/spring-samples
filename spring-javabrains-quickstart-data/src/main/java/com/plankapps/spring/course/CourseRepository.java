package com.plankapps.spring.course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, String> {
	
	//Works without implementation if naming convention is followd. findBy<Classname><AttributeName>
	public List<Course> findByTopicId(String topicId);

}
