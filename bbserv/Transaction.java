import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction{
    public enum Type{deposit,withdraw,transfer,account};  //enum to compare type of transaction
    private String acct1 = null; //primary account involved in transaction
    private Dude acct2 = null; //secondary account involved in transaction
    private Double fee; //fee of the transaction
    private boolean undo = false; //the type of transaction to undo
    private double amount;

    /**
     * Builds the log to be written to the primary and/or secondary account
     * @param type String containing the type of transaction (deposit,withdraw,transfer,account,modiyf)
     * @return String object of the log to be written to the file.
     */
    private String logbuilder(Type type)
    {
        Date currentDate = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        StringBuilder log = new StringBuilder();
        if(undo==false){
            switch (type){
                case deposit:
                    log.append(acct1+" Deposited "+getAmount()+" /"+formatDate.format(currentDate));
                    break;
                case withdraw:
                    log.append(acct1+" Withdrew "+getAmount()+" /"+formatDate.format(currentDate));
                    break;
                case transfer:
                    log.append(acct1+" Transfer "+getAmount()+" to "+acct2.username+"'s account /"+formatDate.format(currentDate));
                    break;
                case account:
                    log.append(acct1+" opened an account.\n");
                    log.append("INITIAL DEPOSIT "+getAmount()+" /"+formatDate.format(currentDate));
                    break;
            }
        }else{ //add undo transaction to the log
            switch (type) {
                case deposit:
                    log.append("UNDONE - DEPOSIT " + this.getAmount() + "/" + formatDate.format(currentDate));
                    break;
                case withdraw:
                    log.append("UNDONE - WITHDRAW " + this.getAmount() + "/" + formatDate.format(currentDate));
                    break;
                case transfer:
                    log.append("UNDONE - TRANSFER " + this.getAmount() + " to account " + this.acct2 + "/" + formatDate.format(currentDate));
                    break;
            }
        }

        if((this.fee > 0) && (undo==true)){ //log for fee reversal
            log.append("\nUNDONE FEE "+this.fee);
        }else if(this.fee >0){ //log for fee associated with transaction
            log.append("\nFee for "+type+":"+this.fee);
        }
        return log.toString(); //return log string
    }

    /**
     * Writes the transaction type to the logfile of a specified account or accounts in the case of a transfer.
     * @param type specifies the type of transaction (deposit,withdraw,transfer,account,modify)
     * @param acct1 Account object one for the primary account to write the log
     * @param acct2 Account object two (can be null) to specify the account to write the log in the event of a transfer
     * @param amt int specify the amount of money involved in the transaction
     */
    public void writeLog(Type type,String acct1,Dude acct2,double amt,double fee,boolean undo){
        this.acct1 = acct1; //set the primary account
        this.acct2 = acct2; //set the secondary account
        this.setAmount(amt); //set the amount invovled in transaction
        this.fee = fee; //set fee of transaction
        this.undo = undo; //set undo flag (true or false)

        String log = this.logbuilder(type); //call the logbuilder to build the log

        /**
         * TODO perform file directory check
         */
        //File dir =  new File("src/logs/"); //directory to store logs
        //File dir  = new File("bbserv"+File.separator+"logs"); //directory to store logs
        //File file = new File(dir+File.separator+this.acct1.toString()+"_log"); //set file name
        File file = new File("bbserv_transaction_log");

        //check if directory exists; create directory if not
        /*if(!dir.exists()){
            if(!file.mkdir()){
                System.out.println("Error: unable to create directory to store logs."); //print error if unable to mkdir
            }
        }*/

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
    }

    private void setAmount(double value)
    {
        if (this.amount != value) {

            double oldValue = this.amount;
            this.amount = value;
            //this.firePropertyChange(PROPERTY_AMOUNT, oldValue, value);
        }
    }

    private double getAmount()
    {
        return this.amount;
    }
}