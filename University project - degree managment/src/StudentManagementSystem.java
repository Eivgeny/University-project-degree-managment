import javax.crypto.Cipher;
import java.util.Date;

public class StudentManagementSystem {

	private int failTreshold;
	private List<Student> students;
	private List<Degree> degrees;
	private List<Course> courses;

	public StudentManagementSystem(int failTreshold) {
		this.failTreshold = failTreshold;
		this.students = new LinkedList<Student>();
		this.degrees = new LinkedList<Degree>();
		this.courses = new LinkedList<Course>();
	}

	public boolean addStudent(Student student){
		for(Student studentFrom : this.students){
			if(studentFrom.equals(student))return false;
		}
		for(Degree degreeFrom : this.degrees){
			if(degreeFrom.getDegreeCode() == student.getDegree().getDegreeCode()){
				this.students.add(student);
				return true;
			}
		}
		return false;
	}

	public boolean addCourse(Course course){
		for(Course courseFrom : this.courses){
			if(courseFrom.equals(course))return false;
		}
		if(this.courses.containsAll(course.getAllPreliminaryCourses())){
			this.courses.add(course);
			return true;
		}
		return false;
	}

	public boolean addDegree(Degree degree) {
		if(this.degrees.contains(degree))return false;

		if(!this.courses.containsAll(degree.getMandatoryCourses())){
			return false;
		}
		if(!this.courses.containsAll(degree.getElectiveCourses())){
			return false;
		}
		this.degrees.add(degree);
		return true;
	}

	private Student studentById(int studentId){
		for(Student studentFrom : this.students){
			if(studentFrom.getStudentInfo().getIdentityNumber() == studentId){
				return studentFrom;
			}
		}
		return null;
	}


	public List<Course> getMissingPreCourses(Course course, int studentId){
		List<Course> listOfCourses =  new LinkedList<Course>(course.getAllPreliminaryCourses());
		Student student = studentById(studentId);

		if(student==null)return null;
		for(Grade gradeFrom : student.getGrades()){
				listOfCourses.remove(gradeFrom.getCourse());
		}

		return listOfCourses;
	}

	public boolean register(int studentId, Course course){


		Student student = studentById(studentId);
		if(student==null)return false; // student check

		if(!this.courses.contains(course)) return false; // course check

		List<Course> listOfCorses = new LinkedList<Course>(); // dagree check
		listOfCorses.addAll(student.getDegree().getElectiveCourses());
		listOfCorses.addAll(student.getDegree().getMandatoryCourses());
		if(!listOfCorses.contains(course))return false;

		listOfCorses = new LinkedList<Course>(course.getAllPreliminaryCourses());//check all preliminary courses are done
		for(Grade gradeFrom : student.getGrades()){
			if(gradeFrom.getCourse().equals(course)){ // if the course already done
				if(gradeFrom.getGrade()>=failTreshold)return false;
			}
			listOfCorses.remove(gradeFrom.getCourse());
		}
		if(listOfCorses.size()!=0)return false;

		student.registerTo(course);
		return true;
	}

	public boolean addGrade(Course course, int studentId, int grade){

		Student student = studentById(studentId);
		if(student==null)return false;//student check
		if(!this.courses.contains(course))return false;//course check
		if(!student.getRegisteredCourses().contains(course))return false;//check register

		for(Grade gradeFrom : student.getGrades()){//check if the course has passed
			if(gradeFrom.getCourse().equals(course)){
				if(gradeFrom.getGrade()>=failTreshold)return false;
			}
		}

		student.addGrade(course,grade);
		return true;

	}


	public List<Student> closeCourse(Course course, List<Pair<Integer, Integer>> grades)
	{
		List<Student> listOfFails = new LinkedList<Student>();
		for(Pair<Integer,Integer> pair : grades){
			if(addGrade(course,pair.getFirst(),pair.getSecond())){
				if(pair.getSecond()<failTreshold){
					listOfFails.add(studentById(pair.getFirst()));
				}
			}
		}
		return listOfFails;
	}

	private boolean gradeContain(Student st,Course course){
		for(Grade grade : st.getGrades()){
			if(grade.getCourse().equals(course))return true;
		}
		return false;
	}

	private int creditsOfStudent(Student student){
		int credits = 0;
		for(Grade grade : student.getGrades()){
			credits += grade.getCourse().getCredit();
		}
		return credits;
	}

	private List<Course> getAllPassedCourses(Student student){
		List<Course> listOfCourses = new LinkedList<>();
		for(Grade grade : student.getGrades()){
			if(grade.getGrade()>=failTreshold) {
				listOfCourses.add(grade.getCourse());
			}
		}
		return listOfCourses;
	}

	public boolean closeDegree(int studentId, int year){

		Student student = studentById(studentId);
		if(student==null)return false;//student check

		if(student.getRegisteredCourses().size() != 0)return false; //check if student registered to some course

		List<Course> coursesToPass = new LinkedList<>(student.getDegree().getMandatoryCourses());
		List<Course> coursesToChoose = new LinkedList<>(student.getDegree().getElectiveCourses());
		int nakaz = 0;
		for(Grade grade : student.getGrades()){
			if(grade.getGrade()>=failTreshold){
				coursesToChoose.remove(grade.getCourse());
				coursesToPass.remove(grade.getCourse());
				nakaz += grade.getCourse().getCredit();
			}
		}
		if(coursesToPass.size()!=0)return false;

		if(student.getDegree().getRequiredCredits()>nakaz)return false;
		student.closeDegree(year);
		return true;
	}


	public List<Student> getFirstKStudents(Degree degree, int year, int k) throws IllegalArgumentException
	{
		if((k%1!=0)||(k<=0)||(year%1!=0)||(year<=0)||(degree == null))throw new IllegalArgumentException("wrong  input");

		List<Student> listOfStudents = new LinkedList<>();
		for(Student student : this.students){
			if(student.getFinishYear()==year){
				listOfStudents.add(student); // get list of all students of that finish year
			}
		}
		List<Student> listOfKStudents = new LinkedList<>(); // empty list to fill the kStudents


		Student kStudent;
		for(int i = 0;i<k;i++){
			if(listOfStudents.size()==0) break;
			kStudent = listOfStudents.get(0);
			for(Student student : listOfStudents){
				if(kStudent.compareTo(student)<0){
					kStudent = student;
				}
			}
			listOfStudents.remove(kStudent);
			listOfKStudents.add(kStudent);
			i++;
		}
		return listOfKStudents;
	}

	private boolean isStudentFail(Student student,Course course){
		for(Grade grade : student.getGrades()){
			if(grade.getCourse().equals(course)){
				if(grade.getGrade()<failTreshold)return true;//if fails
				return false;
			}
		}
		return false;
	}

	public List<Student> getFailStudents(Course course)
	{
		List<Student> failStudents = new LinkedList<>();
		for(Student student : this.students){
			if(isStudentFail(student,course))failStudents.add(student);
		}
		return failStudents;
	}

	public List<Student> getRegisteredStudents(Course course)
	{
		List<Student> registeredStudents = new LinkedList<>();
		for(Student student : this.students){
			if(student.isRegisteredTo(course))registeredStudents.add(student);
		}
		return registeredStudents;
	}

	public List<Course> nextAvailableCourses(int studentId)
	{
		Student student = studentById(studentId);
		List<Course> passedCourses = new LinkedList<>(getAllPassedCourses(student));
		List<Course> availableCourses = new LinkedList<>(student.getDegree().getMandatoryCourses());
		availableCourses.addAll(student.getDegree().getElectiveCourses());
		availableCourses.removeAll(passedCourses);

		for(Course course : availableCourses){
			if(!passedCourses.containsAll(course.getAllPreliminaryCourses()))availableCourses.remove(course);
		}
		return availableCourses;
	}

	public List<Course> getMissingCourses(int studentId)
	{
		Student student = studentById(studentId);
		List<Course> coursesToPass = new LinkedList<>(student.getDegree().getMandatoryCourses());
		coursesToPass.removeAll(getAllPassedCourses(student));
		return coursesToPass;
	}

	public List<Student> getStudents() {
		return new LinkedList<Student>(this.students);
	}

	public List<Course> getCourses() {
		return new LinkedList<Course>(this.courses);
	}

	public List<Degree> getDegrees() {
		return new LinkedList<Degree>(this.degrees);
	}

	public int getFailTreshold() {
		return this.failTreshold;
	}
}
