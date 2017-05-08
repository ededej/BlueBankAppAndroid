import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction extends Response {

	public boolean isDude = false;
    public String type = ""; //u?[d|w|t|a] - string type of transaction
    public String acc1 = null; //primary account involved in transaction
    public String acc2 = null; //secondary account involved in transaction
    public Double fee; //fee of the transaction
    public Double amount;
	
	private String TDELIM = "%";
	
	public Transaction(){
		amount = 0.0;
	}
	
	public Transaction(String t, String a1, String a2, Double f, Double a){
		type = t;
		acc1 = a1;
		acc2 = a2;
		fee = f;
		amount = a;
	}
	
	public Transaction(String s){
		String[] fields = s.split(TDELIM);
		type = fields[0];
		acc1 = fields[1];
		acc2 = fields[2];
		fee = Double.parseDouble(fields[3]);
		amount = Double.parseDouble(fields[4]);
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append(type + TDELIM);
		s.append(acc1 + TDELIM);
		s.append(acc2 + TDELIM);
		s.append(fee.toString() + TDELIM);
		s.append(amount.toString());
		return s.toString();
	}
	
	public boolean equals(Object o){
		Transaction t = (Transaction) o;
		if (!this.type.equals(t.type)){ return false; }
		if (!this.acc1.equals(t.acc1)){ return false; }
		if (!this.acc2.equals(t.acc2)){ return false; }
		if (!this.amount.equals(t.amount)){ return false; }
		return true;
	}
}