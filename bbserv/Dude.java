public class Dude extends Response {
	public boolean isDude = true;
	public String username;
	public String password;
	public Double balance;
	public String ssn;
	public String dob;
	public String email;
	public String fullname;
	
	public Dude(){
		username = "NEW";
	}
	
	public Dude(String s){
		String[] fields = s.split(DELIM);
		username = fields[0];
		password = fields[1];
		balance = Double.parseDouble(fields[2]);
		ssn = fields[3];
		dob = fields[4];
		email = fields[5];
		fullname = fields[6];
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append(username + DELIM);
		s.append(password + DELIM);
		s.append(balance.toString() + DELIM);
		s.append(ssn + DELIM);
		s.append(dob + DELIM);
		s.append(email + DELIM);
		s.append(fullname);
		return s.toString();
	}
}