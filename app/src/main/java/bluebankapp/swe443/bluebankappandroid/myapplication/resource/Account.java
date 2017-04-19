/*
   Copyright (c) 2017 Shelby
   
   Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
   and associated documentation files (the "Software"), to deal in the Software without restriction, 
   including without limitation the rights to use, copy, modify, merge, publish, distribute, 
   sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
   furnished to do so, subject to the following conditions: 
   
   The above copyright notice and this permission notice shall be included in all copies or 
   substantial portions of the Software. 
   
   The Software shall be used for Good, not Evil. 
   
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
   BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
   DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
   
package bluebankapp.swe443.bluebankappandroid.myapplication.resource;

import android.os.Parcel;
import android.os.Parcelable;

import de.uniks.networkparser.interfaces.SendableEntity;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import de.uniks.networkparser.EntityUtil;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;
   /**
    * 
    * @see <a href='../../../../../../../../../app/src/test/java/bluebankapp/swe443/bluebankappandroid/ExampleUnitTest.java'>ExampleUnitTest.java</a>
 */
   public  class Account implements SendableEntity, Parcelable {

   
   //==========================================================================
   public void transfer(  )
   {

   }

   
   //==========================================================================
   
   protected PropertyChangeSupport listeners = null;
   
   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null) {
   		listeners.firePropertyChange(propertyName, oldValue, newValue);
   		return true;
   	}
   	return false;
   }
   
   public boolean addPropertyChangeListener(PropertyChangeListener listener) 
   {
   	if (listeners == null) {
   		listeners = new PropertyChangeSupport(this);
   	}
   	listeners.addPropertyChangeListener(listener);
   	return true;
   }
   
   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
   	if (listeners == null) {
   		listeners = new PropertyChangeSupport(this);
   	}
   	listeners.addPropertyChangeListener(propertyName, listener);
   	return true;
   }
   
   public boolean removePropertyChangeListener(PropertyChangeListener listener) {
   	if (listeners == null) {
   		listeners.removePropertyChangeListener(listener);
   	}
   	listeners.removePropertyChangeListener(listener);
   	return true;
   }

   public boolean removePropertyChangeListener(String propertyName,PropertyChangeListener listener) {
   	if (listeners != null) {
   		listeners.removePropertyChangeListener(propertyName, listener);
   	}
   	return true;
   }

   
   //==========================================================================
   
   
   public void removeYou()
   {
      setUser_Has(null);
      setBank_has(null);
      firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_NAME = "name";
   
   private String name;

   public String getName()
   {
      return this.name;
   }
   
   public void setName(String value)
   {
      if ( ! EntityUtil.stringEquals(this.name, value)) {
      
         String oldValue = this.name;
         this.name = value;
         this.firePropertyChange(PROPERTY_NAME, oldValue, value);
      }
   }
   
   public Account withName(String value)
   {
      setName(value);
      return this;
   } 


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getName());
      result.append(" ").append(this.getSsn());
      result.append(" ").append(this.getUsername());
      result.append(" ").append(this.getPassword());
      result.append(" ").append(this.getInitialAmount());
      result.append(" ").append(this.getAccountBalance());
      result.append(" ").append(this.getRecentTransaction());
      result.append(" ").append(this.getIOweTheBank());
      result.append(" ").append(this.getDob());
      return result.substring(1);
   }


   
   //==========================================================================
   
   public static final String PROPERTY_SSN = "ssn";
   
   private int ssn;

   public int getSsn()
   {
      return this.ssn;
   }
   
   public void setSsn(int value)
   {
      if (this.ssn != value) {
      
         int oldValue = this.ssn;
         this.ssn = value;
         this.firePropertyChange(PROPERTY_SSN, oldValue, value);
      }
   }
   
   public Account withSsn(int value)
   {
      setSsn(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_DOB = "dob";
   
   private String dob;

   public String getDob()
   {
      return this.dob;
   }
   
   public void setDob(String value)
   {
      if (this.dob != value) {
      
         String oldValue = this.dob;
         this.dob = value;
         this.firePropertyChange(PROPERTY_DOB, oldValue, value);
      }
   }
   
   public Account withDob(String value)
   {
      setDob(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_USERNAME = "username";
   
   private String username;

   public String getUsername()
   {
      return this.username;
   }
   
   public void setUsername(String value)
   {
      if ( ! EntityUtil.stringEquals(this.username, value)) {
      
         String oldValue = this.username;
         this.username = value;
         this.firePropertyChange(PROPERTY_USERNAME, oldValue, value);
      }
   }
   
   public Account withUsername(String value)
   {
      setUsername(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_PASSWORD = "password";
   
   private String password;

   public String getPassword()
   {
      return this.password;
   }
   
   public void setPassword(String value)
   {
      if ( ! EntityUtil.stringEquals(this.password, value)) {
      
         String oldValue = this.password;
         this.password = value;
         this.firePropertyChange(PROPERTY_PASSWORD, oldValue, value);
      }
   }
   
   public Account withPassword(String value)
   {
      setPassword(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_INITIALAMOUNT = "initialAmount";
   
   private double initialAmount;

   public double getInitialAmount()
   {
      return this.initialAmount;
   }
   
   public void setInitialAmount(double value)
   {
      if (this.initialAmount != value) {
      
         double oldValue = this.initialAmount;
         this.initialAmount = value;
         this.firePropertyChange(PROPERTY_INITIALAMOUNT, oldValue, value);
      }
   }
   
   public Account withInitialAmount(double value)
   {
      setInitialAmount(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_ACCOUNTBALANCE = "accountBalance";
   
   private double accountBalance;

   public double getAccountBalance()
   {
      return this.accountBalance;
   }
   
   public void setAccountBalance(double value)
   {
      if (this.accountBalance != value) {
      
         double oldValue = this.accountBalance;
         this.accountBalance = value;
         this.firePropertyChange(PROPERTY_ACCOUNTBALANCE, oldValue, value);
      }
   }
   
   public Account withAccountBalance(double value)
   {
      setAccountBalance(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_RECENTTRANSACTION = "recentTransaction";
   
   private String recentTransaction;

   public String getRecentTransaction()
   {
      return this.recentTransaction;
   }
   
   public void setRecentTransaction(String value)
   {
      if ( ! EntityUtil.stringEquals(this.recentTransaction, value)) {
      
         String oldValue = this.recentTransaction;
         this.recentTransaction = value;
         this.firePropertyChange(PROPERTY_RECENTTRANSACTION, oldValue, value);
      }
   }
   
   public Account withRecentTransaction(String value)
   {
      setRecentTransaction(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_IOWETHEBANK = "iOweTheBank";
   
   private double iOweTheBank;

   public double getIOweTheBank()
   {
      return this.iOweTheBank;
   }
   
   public void setIOweTheBank(double value)
   {
      if (this.iOweTheBank != value) {
      
         double oldValue = this.iOweTheBank;
         this.iOweTheBank = value;
         this.firePropertyChange(PROPERTY_IOWETHEBANK, oldValue, value);
      }
   }
   
   public Account withIOweTheBank(double value)
   {
      setIOweTheBank(value);
      return this;
   } 

   
   /********************************************************************
    * <pre>
    *              many                       one
    * Account ----------------------------------- User
    *              Account_Has                   User_Has
    * </pre>
    */
   
   public static final String PROPERTY_USER_HAS = "User_Has";

   private User User_Has = null;

   public User getUser_Has()
   {
      return this.User_Has;
   }

   public boolean setUser_Has(User value)
   {
      boolean changed = false;
      
      if (this.User_Has != value)
      {
         User oldValue = this.User_Has;
         
         if (this.User_Has != null)
         {
            this.User_Has = null;
            oldValue.withoutAccount_Has(this);
         }
         
         this.User_Has = value;
         
         if (value != null)
         {
            value.withAccount_Has(this);
         }
         
         firePropertyChange(PROPERTY_USER_HAS, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Account withUser_Has(User value)
   {
      setUser_Has(value);
      return this;
   } 

   public User createUser_Has()
   {
      User value = new User();
      withUser_Has(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              many                       one
    * Account ----------------------------------- Bank
    *              Account_Has                   Bank_has
    * </pre>
    */
   
   public static final String PROPERTY_BANK_HAS = "Bank_has";

   private Bank Bank_has = null;

   public Bank getBank_has()
   {
      return this.Bank_has;
   }

   public boolean setBank_has(Bank value)
   {
      boolean changed = false;
      
      if (this.Bank_has != value)
      {
         Bank oldValue = this.Bank_has;
         
         if (this.Bank_has != null)
         {
            this.Bank_has = null;
            oldValue.withoutAccount_Has(this);
         }
         
         this.Bank_has = value;
         
         if (value != null)
         {
            value.withAccount_Has(this);
         }
         
         firePropertyChange(PROPERTY_BANK_HAS, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Account withBank_has(Bank value)
   {
      setBank_has(value);
      return this;
   } 

   public Bank createBank_has()
   {
      Bank value = new Bank();
      withBank_has(value);
      return value;
   }

      public Account() {
      }

      @Override
      public int describeContents() {
         return 0;
      }

      @Override
      public void writeToParcel(Parcel dest, int flags) {
         dest.writeSerializable(this.listeners);
         dest.writeString(this.name);
         dest.writeInt(this.ssn);
         dest.writeString(this.dob);
         dest.writeString(this.username);
         dest.writeString(this.password);
         dest.writeDouble(this.initialAmount);
         dest.writeDouble(this.accountBalance);
         dest.writeString(this.recentTransaction);
         dest.writeDouble(this.iOweTheBank);
         dest.writeParcelable(this.User_Has, flags);
         dest.writeParcelable(this.Bank_has, flags);
      }

      protected Account(Parcel in) {
         this.listeners = (PropertyChangeSupport) in.readSerializable();
         this.name = in.readString();
         this.ssn = in.readInt();
         this.dob = in.readString();
         this.username = in.readString();
         this.password = in.readString();
         this.initialAmount = in.readDouble();
         this.accountBalance = in.readDouble();
         this.recentTransaction = in.readString();
         this.iOweTheBank = in.readDouble();
         this.User_Has = in.readParcelable(User.class.getClassLoader());
         this.Bank_has = in.readParcelable(Bank.class.getClassLoader());
      }

      public static final Creator<Account> CREATOR = new Creator<Account>() {
         @Override
         public Account createFromParcel(Parcel source) {
            return new Account(source);
         }

         @Override
         public Account[] newArray(int size) {
            return new Account[size];
         }
      };


      //==========================================================================
      public void deposit(double amt) {
         /**
          * check if valid amount. add amount to balance if greater than 0.
          */
         //Bank.setDepositFee(0.05);
         //double fee = amt*Bank.getDepositFee();
         if (amt >= 0 ) {
            double amount = amt ;//- fee;
            //this.iOweTheBank += fee;
            this.setAccountBalance(amount + getAccountBalance()); //add amount to the account balance.
            this.recentTransaction = "deposit " + amt;

            //log the deposit transaction for this account
          //  new Transaction().writeLog(Transaction.Type.deposit,this,null,amt,fee,false);
         } else {
            throw new IllegalArgumentException(amt + " is less than or equal to 0");
         }
      }


      //==========================================================================
      public double withdraw(double amt) {
         double withdraw_amt=0;
         //Bank.setWithdrawFee(0.05);
         //double fee = amt*Bank.getWithdrawFee();
         double amtandfee = amt ;//+ fee;
         //Check if amount to withdraw results in a negative balance
         if ((this.getAccountBalance() - amtandfee) < 0) {
            withdraw_amt =  -1; //return -1; do not do withdrawal
         }else{

            //Check if amount to withdraw results in a positive balance
            if((this.getAccountBalance() - amtandfee) > 0){

               this.setAccountBalance(this.getAccountBalance() - amtandfee); //deduct amount from balance. Update balance.
              // this.iOweTheBank += fee;
               this.recentTransaction = "withdrawal " + amt;
               withdraw_amt =  amt; //return requested amount

               //log the deposit transaction for this account
               //new Transaction().writeLog(Transaction.Type.withdraw,this,null,amt,fee,false);
            }
         }

         return withdraw_amt;
      }

   }
