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
	
	
	
	/*
    private String logbuilder(Type type)
    {
        Date currentDate = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");

        StringBuilder log = new StringBuilder();
        if (undo==false) {
            switch (type){
                case deposit:
                    log.append(acct1+" Deposited "+getAmount()+" /"+formatDate.format(currentDate));
                    break;
                case withdraw:
                    log.append(acct1+" Withdrew "+getAmount()+" /"+formatDate.format(currentDate));
                    break;
                case transfer:
                    log.append(acct1+" Transferred "+getAmount()+" to "+acct2.username+" /"+formatDate.format(currentDate));
                    break;
                case account:
                    log.append(acct1+" Created Account");
                    log.append(acct1+" Deposited "+getAmount()+" /"+formatDate.format(currentDate));
                    break;
            }
        } else { //add undo transaction to the log
            switch (type) {
                case deposit:
                    log.append(" Undo Deposit " + getAmount() + "/" + formatDate.format(currentDate));
                    break;
                case withdraw:
                    log.append(" Undo Withdraw " + getAmount() + "/" + formatDate.format(currentDate));
                    break;
                case transfer:
                    log.append(" Undo Transfer " + getAmount() + " to " + acct2 + "/" + formatDate.format(currentDate));
                    break;
            }
        }

        if((this.fee > 0) && (undo==true)){ //log for fee reversal
            log.append("\n Undo Fee "+this.fee);
        }else if(this.fee >0){ //log for fee associated with transaction
            log.append("\n Fee "+type+":"+this.fee);
        }
        return log.toString(); //return log string
    }

    public void writeLog(Type type,String acct1,Dude acct2,double amt,double fee,boolean undo){
        this.acct1 = acct1; //set the primary account
        this.acct2 = acct2; //set the secondary account
        this.setAmount(amt); //set the amount invovled in transaction
        this.fee = fee; //set fee of transaction
        this.undo = undo; //set undo flag (true or false)

        String log = this.logbuilder(type); //call the logbuilder to build the log

        //File dir =  new File("src/logs/"); //directory to store logs
        //File dir  = new File("bbserv"+File.separator+"logs"); //directory to store logs
        //File file = new File(dir+File.separator+this.acct1.toString()+"_log"); //set file name
        File file = new File("bbserv_transaction_log");

        //log transaction in account's log file
        try(BufferedWriter write2file = new BufferedWriter(new FileWriter(file,true))){
            //write log to file
            write2file.write(log);
            write2file.newLine();
            write2file.close();

        }catch (Exception e){
            System.out.print("Exception during file write in writeLog function:"+e);
        }

        //log transfer in receivers log
        if(type.equals(Type.transfer)){
            //log to the second account
            String log_2 = acct1+" transferred "+getAmount()+" /"+new SimpleDateFormat("MM-dd-yyyy HH:mm").format(new Date());
            //File file2 = new File("src/logs/"+this.acct2.username.toString()+"_log");

            try(BufferedWriter write2file = new BufferedWriter(new FileWriter(file,true))){
                //write log to file
                write2file.write(log_2);
                write2file.newLine();
                write2file.close();
            }catch (Exception e){
                System.out.print("Exception during file write 2 in writeLog function:"+e);
            }
        }
    } //*/
}