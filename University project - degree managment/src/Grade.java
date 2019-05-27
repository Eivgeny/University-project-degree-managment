public class Grade {

    private Course course;
    private int grade;

    public Grade(Course course, int grade) throws IllegalArgumentException{
        if((course!=null)&&(0<=grade)&&(grade<=101)) {
                this.course = course;
                this.grade = grade;
        }else {
            throw new IllegalArgumentException("wrong input");
        }
    }

    public Course getCourse() {
    	return this.course;
    }

    public int getGrade() {
    	return this.grade;
    }

    public int setGrade(int grade) throws IllegalArgumentException{
        if((0<=grade)&&(grade<=100)){
            int oldGrade = this.grade;
            this.grade = grade;
            return oldGrade;
        }else{
            throw new IllegalArgumentException("wrong input");
        }
    }
    
    public String toString() {
    	return "Course: "+course.getName() + " by number: " + course.getNumber()+ " grade: "+this.grade;
    }

    public boolean equals(Object other){
        if((this!=other)||(other==null))return false;
        if(((Grade)other).getCourse()==(this.getCourse())){
            if(((Grade) other).getGrade()==this.getGrade()){
                return true;
            }
        }
    	return false;
    }
}
