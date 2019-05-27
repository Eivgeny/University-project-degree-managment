import java.util.Date;

public class BachelorDegree extends Degree {
	final static int REQUIRED_CREDITS = 20;
	
    public BachelorDegree(String name, int degreeCode)
    {
        super(name,degreeCode,REQUIRED_CREDITS);
    }
}