package com.plankapps.spring.course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
    public List<Course> getAllCoursesByTopic(String topicId) {
    	List<Course> allCourses = new ArrayList<Course>();
    	courseRepository.findByTopicId(topicId)
    		.forEach(allCourses::add);
        return allCourses;
    }

    public Course getCourse(String id) {
    	return courseRepository.findOne(id);
    }

    public void addCourse(Course course) {
    	courseRepository.save(course);
    }

    public void updateCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(String id) {
        courseRepository.delete(id);
    }
}
