import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Degree {

    private String name;
    private int degreeCode;
    private int requiredCredits;
    private List<Course> mustCourses;
    private List<Course> choosableCourses;

    public Degree(String name, int degreeCode, int requiredCredits) {
        this.name = name;
        this.degreeCode = degreeCode;
        this.requiredCredits = requiredCredits;
        this.mustCourses = new LinkedList<Course>();
        this.choosableCourses = new LinkedList<Course>();
    }


    public String getDegreeName(){
    	return this.name;
    }

    public int getDegreeCode()
    {
    	return this.degreeCode;
    }

    public List<Course> getMandatoryCourses() {
    	return new LinkedList<>(this.mustCourses);
    }

    public List<Course> getElectiveCourses() {
    	return new LinkedList<>(this.choosableCourses);
    }

    public boolean addCourse(Course course,boolean mandatory){
        if(mandatory){
            int newBalance = getMandatoryCredits();
            List<Course> coursesToAdd = new LinkedList<Course>();
            coursesToAdd.add(course);
            coursesToAdd.addAll(course.getAllPreliminaryCourses());

            // מחשב ובודק עם הנקז שנוסף לא חורג את הנקז נדרוש
            for(Course courseFrom : coursesToAdd){
                if(!this.mustCourses.contains(courseFrom)){
                    newBalance += courseFrom.getCredit();
                    if(newBalance>this.requiredCredits){
                        return false;
                    }
                }else {
                    coursesToAdd.remove(courseFrom);
                }
            }
            // במידה והגיע לפה הכל תקין מוסיף את כל הקורסים
            this.mustCourses.addAll(coursesToAdd);
        }else {
            if(this.choosableCourses.contains(course)){
                return false;
            }
            this.choosableCourses.add(course);
        }
    	return true;
    }
    
    public int getRequiredCredits(){
    	return this.requiredCredits;
    }

    public int getMandatoryCredits(){
    	int balance = 0;
    	for(Course courseFrom : this.mustCourses){
    	    balance += courseFrom.getCredit();
        }
        return balance;
    }

}
