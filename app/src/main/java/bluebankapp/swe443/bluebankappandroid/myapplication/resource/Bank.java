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
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.util.AccountSet;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.util.UserSet;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;
   /**
    * 
    * @see <a href='../../../../../../../../../app/src/test/java/bluebankapp/swe443/bluebankappandroid/ExampleUnitTest.java'>ExampleUnitTest.java</a>
 */
   public  class Bank implements SendableEntity, Parcelable {

   
   //==========================================================================
   public Object mainMenu(  )
   {
      return null;
   }

   
   //==========================================================================
   public void createAccount(  )
   {
      
   }

   
   //==========================================================================
   public void logIn(  )
   {
      
   }

   
   //==========================================================================
   public void makeDeposit(  )
   {
      
   }

   
   //==========================================================================
   public void makeWithdrawal(  )
   {
      
   }

   
   //==========================================================================
   public void makeTransfer(  )
   {
      
   }

   
   //==========================================================================
   public void undoMostRecentTransaction(  )
   {
      
   }

   
   //==========================================================================
   public void viewBalance(  )
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
      withoutAccount_Has(this.getAccount_Has().toArray(new Account[this.getAccount_Has().size()]));
      withoutUser_In(this.getUser_In().toArray(new User[this.getUser_In().size()]));
      firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_BANKNAME = "bankName";
   
   private String bankName;

   public String getBankName()
   {
      return this.bankName;
   }
   
   public void setBankName(String value)
   {
      if ( ! EntityUtil.stringEquals(this.bankName, value)) {
      
         String oldValue = this.bankName;
         this.bankName = value;
         this.firePropertyChange(PROPERTY_BANKNAME, oldValue, value);
      }
   }
   
   public Bank withBankName(String value)
   {
      setBankName(value);
      return this;
   } 


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getBankName());
      return result.substring(1);
   }


   
   /********************************************************************
    * <pre>
    *              one                       many
    * Bank ----------------------------------- Account
    *              Bank_has                   Account_Has
    * </pre>
    */
   
   public static final String PROPERTY_ACCOUNT_HAS = "Account_Has";

   private AccountSet Account_Has = null;
   
   public AccountSet getAccount_Has()
   {
      if (this.Account_Has == null)
      {
         return AccountSet.EMPTY_SET;
      }
   
      return this.Account_Has;
   }

   public Bank withAccount_Has(Account... value)
   {
      if(value==null){
         return this;
      }
      for (Account item : value)
      {
         if (item != null)
         {
            if (this.Account_Has == null)
            {
               this.Account_Has = new AccountSet();
            }
            
            boolean changed = this.Account_Has.add (item);

            if (changed)
            {
               item.withBank_has(this);
               firePropertyChange(PROPERTY_ACCOUNT_HAS, null, item);
            }
         }
      }
      return this;
   } 

   public Bank withoutAccount_Has(Account... value)
   {
      for (Account item : value)
      {
         if ((this.Account_Has != null) && (item != null))
         {
            if (this.Account_Has.remove(item))
            {
               item.setBank_has(null);
               firePropertyChange(PROPERTY_ACCOUNT_HAS, item, null);
            }
         }
      }
      return this;
   }

   public Account createAccount_Has()
   {
      Account value = new Account();
      withAccount_Has(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Bank ----------------------------------- User
    *              Bank_has                   User_In
    * </pre>
    */
   
   public static final String PROPERTY_USER_IN = "User_In";

   private UserSet User_In = null;
   
   public UserSet getUser_In()
   {
      if (this.User_In == null)
      {
         return UserSet.EMPTY_SET;
      }
   
      return this.User_In;
   }

   public Bank withUser_In(User... value)
   {
      if(value==null){
         return this;
      }
      for (User item : value)
      {
         if (item != null)
         {
            if (this.User_In == null)
            {
               this.User_In = new UserSet();
            }
            
            boolean changed = this.User_In.add (item);

            if (changed)
            {
               item.withBank_has(this);
               firePropertyChange(PROPERTY_USER_IN, null, item);
            }
         }
      }
      return this;
   } 

   public Bank withoutUser_In(User... value)
   {
      for (User item : value)
      {
         if ((this.User_In != null) && (item != null))
         {
            if (this.User_In.remove(item))
            {
               item.setBank_has(null);
               firePropertyChange(PROPERTY_USER_IN, item, null);
            }
         }
      }
      return this;
   }

   public User createUser_In()
   {
      User value = new User();
      withUser_In(value);
      return value;
   }

      @Override
      public int describeContents() {
         return 0;
      }

      @Override
      public void writeToParcel(Parcel dest, int flags) {
         dest.writeSerializable(this.listeners);
         dest.writeString(this.bankName);
         dest.writeParcelable((Parcelable) this.Account_Has, flags);
         dest.writeParcelable((Parcelable) this.User_In, flags);
      }

      public Bank() {
      }

      protected Bank(Parcel in) {
         this.listeners = (PropertyChangeSupport) in.readSerializable();
         this.bankName = in.readString();
         this.Account_Has = in.readParcelable(AccountSet.class.getClassLoader());
         this.User_In = in.readParcelable(UserSet.class.getClassLoader());
      }

      public static final Parcelable.Creator<Bank> CREATOR = new Parcelable.Creator<Bank>() {
         @Override
         public Bank createFromParcel(Parcel source) {
            return new Bank(source);
         }

         @Override
         public Bank[] newArray(int size) {
            return new Bank[size];
         }
      };
   }
