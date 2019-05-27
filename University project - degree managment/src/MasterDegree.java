
public class MasterDegree extends Degree {
	final static int REQUIRED_CREDITS = 10;

	private boolean withResearch;


	public MasterDegree(String name, int degreeCode, boolean withResearch) {
        super(name,degreeCode,REQUIRED_CREDITS);
        this.withResearch = withResearch;
    }
    
    public boolean getWithResearch(){
    	return withResearch;
    }
}