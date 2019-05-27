public class Student implements Comparable<Student>{

	private StudentInfo studentInfo;
	private Degree degree;
	private List<Course> registeredCourses;
	private List<Grade> grades;
	private int currentYear;
	private int finishAt;

	//constructors
	public Student(StudentInfo studentInfo, Degree degree) {
		if (studentInfo == null | degree == null)
			throw new IllegalArgumentException();

		this.studentInfo = studentInfo;
		this.degree = degree;
		
		this.registeredCourses = new LinkedList<Course>();
		this.grades = new LinkedList<Grade>();
		this.currentYear = 1;
		this.finishAt = 0;
		
	}
	
	//methods
	public StudentInfo getStudentInfo(){
		return studentInfo;
	}
	
	public Degree getDegree() {
		return degree;
	}
	
	public int getCurrentYear() {
		return currentYear;
	}
	
	public int getFinishYear() {
		return finishAt; 
	}

	public List<Grade> getGrades() {
		return grades;
	}
	public void increaseYear() {
		this.currentYear += 1;
	}
	
	public void closeDegree(int year) {
		this.currentYear = 0;
		this.finishAt = year;
	}
	
	public boolean isRegisteredTo(Course course){
		return this.registeredCourses.contains(course);
	}
	
	public boolean isCompleted(Course course, int passGrade){
		for(Grade gradeFrom : grades){
			if(gradeFrom.getCourse().equals(course)){
				if(gradeFrom.getGrade() >= passGrade){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean registerTo(Course course){
		if(registeredCourses.contains(course))return false;
		this.registeredCourses.add(course);
		return true;
	}
	
	public double averageGrade() {
		double avg = 0.0;
		int credit = 0;
		for(Grade grade : this.grades){
			avg += grade.getCourse().getCredit()*grade.getGrade();
			credit += grade.getCourse().getCredit();
		}
		avg = avg / credit;
		return avg;
	}

	public boolean addGrade(Course course, int grade) {
		if(this.registeredCourses.contains(course)){
			this.grades.add(new Grade(course,grade));
			this.registeredCourses.remove(course);
			return true;
		}
		return false;
	}
	
	public int setGrade(Course course, int grade) throws IllegalArgumentException {
		int oldGrade;
		for(Grade gradeFrom : this.grades){
			if(gradeFrom.getCourse() == course){
				oldGrade = gradeFrom.getGrade();
				gradeFrom.setGrade(grade);
				return oldGrade;
			}
		}
		throw new IllegalArgumentException("No such course");
	}
	
	public String toString() {
		return null;
	}
	
	public boolean equals(Object other) {
		return other instanceof Student && studentInfo.getIdentityNumber() == ((Student) other).studentInfo.getIdentityNumber();
	}

	public List<Course> getRegisteredCourses() {
		return new LinkedList<>(this.registeredCourses);
	}

	public Grade getCourseGrade(Course c)
	{
		for(Grade gradeFrom : this.grades){
			if(gradeFrom.getCourse() == c){
				return gradeFrom;
			}
		}
		return null;
	}

	@Override
	public int compareTo(Student o) {
		double thisStudent = this.averageGrade();
		double otherStudent = o.averageGrade();
		if(thisStudent>otherStudent)return 1;
		if(thisStudent<otherStudent)return -1;
		int thisId = this.getStudentInfo().getIdentityNumber();
		int otherId = o.getStudentInfo().getIdentityNumber();
		if(thisId>otherId)return 1;
		if(thisId<otherId)return -1;
		return 0;
	}
}
