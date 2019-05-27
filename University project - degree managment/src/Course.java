import java.util.ArrayList;
import java.util.Collections;

public class Course implements Comparable<Course>{

	private String name;
	private int number;
	private int credit;
	private List<Course> preCourses;


	public Course(String name, int number, int credit) {
		if (name == null || name.equals("") | number <=0 | credit <= 0 | credit > 6| !onlyLettersAndSpaces(name))
			throw new IllegalArgumentException();

		this.name = name;
		this.number = number;
		this.credit = credit;
		this.preCourses = new LinkedList<Course>();
	}

	public String getName()
	{
		return name;
	}

	public int getNumber()
	{
		return this.number;
	}

	public int getCredit()
	{
		return credit;
	}

	public String toString(){
		return "Course: name - " + name + ", number - " + number + ", credit - " + credit + ".";
	}

	public boolean equals(Object other){
		return other instanceof Course && ((Course) other).number == this.number;}


	public List<Course> getPreliminaryCourses()	{
		return sortList(this.preCourses);
	}

	public List<Course> getAllPreliminaryCourses(){
		Boolean preCourseAdded = false;
		List<Course> temp = new LinkedList<Course>();
		for(Course course : this.preCourses){
			if(!preCourseAdded) {
				temp.addAll(this.getPreliminaryCourses());
			}
			preCourseAdded = true;
			for(Course courseFrom : course.getAllPreliminaryCourses()){
				if(!temp.contains(courseFrom)) {
					temp.add(courseFrom);
				}
			}
			//temp.addAll(course.getAllPreliminaryCourses());
		}
		return temp;
	}

	private List<Course> sortList(List<Course> listOfCources) {

		List<Course> tempList = new LinkedList<Course>();
		List<Course> CurrentList = new LinkedList<Course>();
		CurrentList.addAll(listOfCources);

		Course maxCourse, testedCourse;
		while (!CurrentList.isEmpty()) {
			maxCourse = CurrentList.get(0);
			for (int i = 0; i < CurrentList.size(); i++) {
				testedCourse = CurrentList.get(i);
				if (maxCourse.compareTo(testedCourse) <= 0) {
					maxCourse = testedCourse;
				}
			}
			tempList.add(maxCourse);
			CurrentList.remove(maxCourse);
		}
		return tempList;
	}

	public void addPreliminaryCourses(List<Course> courses){
		this.preCourses.addAll(courses);
	}
	public void addPreliminaryCourse(Course course){
		this.preCourses.add(course);
	}

	public boolean isPreliminaryCourse(Course other){
		for(Course c : other.preCourses){
			if(c.getNumber() == this.getNumber()){
				return true;
			}
		}
		return false;
	}



	@Override
	public int compareTo(Course other) {
		if(this.isPreliminaryCourse(other)){
			return -1;
		}
		return this.getNumber() - other.getNumber();
	}

	private boolean onlyLettersAndSpaces(String str) {
		boolean isLetter = true;
		for (int i = 0; i < str.length() & isLetter ; i++) {
			char c = str.charAt(i);
			isLetter = c == ' ' | (c >= 'a' & c <= 'z') | (c >= 'A' & c <= 'Z') | '0' <= c & c <= '9';
		}
		return isLetter;
	}
}
