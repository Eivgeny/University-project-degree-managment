public class StudentInfo {
	private String firstName;
	private String familyName;
	private int identityNumber;
	private String address;

	public StudentInfo(String firstName, String familyName, int identityNumber, String address) {
		if (firstName == null || firstName.equals("") |
				familyName == null || familyName.equals("") |
				!(100000000 <= identityNumber & identityNumber < 1000000000) |
				address == null || !onlyLettersAndSpaces(firstName) || !onlyLettersAndSpaces(familyName))
			throw new IllegalArgumentException();
		this.firstName = firstName;
		this.familyName = familyName;
		this.identityNumber = identityNumber;
		this.address = address;
	}

	public String getFirstName() {
		return firstName;}

	public String getFamilyName() {
		return familyName;}

	public int getIdentityNumber() {
		return identityNumber;}

	public void setAddress(String address) {
		if (address == null || !onlyLettersAndSpacesAndNumber(address))
			throw new IllegalArgumentException();
		this.address = address;
	}

	private boolean onlyLettersAndSpaces(String address) {
		boolean isLetter = true;
		for (int i = 0; i < address.length() & isLetter ; i++) {
			char c = address.charAt(i);
			isLetter = c == ' ' | (c >= 'a' & c <= 'z') | (c >= 'A' & c <= 'Z');
		}
		return isLetter;
	}

	private boolean onlyLettersAndSpacesAndNumber(String address) {
		boolean isLetter = true;
		for (int i = 0; i < address.length() & isLetter ; i++) {
			char c = address.charAt(i);
			isLetter = c == ' ' | (c >= 'a' & c <= 'z') | (c >= 'A' & c <= 'Z') | (c>= '0' & c <= '9');
		}
		return isLetter;
	}
	
	public String getAddress(){
		return address;
	}
	
	
}