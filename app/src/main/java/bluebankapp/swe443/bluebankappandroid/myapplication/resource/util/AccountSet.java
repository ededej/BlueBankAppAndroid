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
   
package bluebankapp.swe443.bluebankappandroid.myapplication.resource.util;

import android.os.Parcel;
import android.os.Parcelable;

import de.uniks.networkparser.list.SimpleSet;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import de.uniks.networkparser.interfaces.Condition;
import java.util.Collection;
import de.uniks.networkparser.list.ObjectSet;
import de.uniks.networkparser.list.NumberList;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.util.UserSet;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.util.BankSet;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;

public class AccountSet extends SimpleSet<Account> implements Parcelable {
   protected AccountSet(Parcel in) {
   }

   public static final Creator<AccountSet> CREATOR = new Creator<AccountSet>() {
      @Override
      public AccountSet createFromParcel(Parcel in) {
         return new AccountSet(in);
      }

      @Override
      public AccountSet[] newArray(int size) {
         return new AccountSet[size];
      }
   };

   protected Class<?> getTypClass() {
		return Account.class;
	}

   public AccountSet()
   {
      // empty
   }

   public AccountSet(Account... objects)
   {
      for (Account obj : objects)
      {
         this.add(obj);
      }
   }

   public AccountSet(Collection<Account> objects)
   {
      this.addAll(objects);
   }

   public static final AccountSet EMPTY_SET = new AccountSet().withFlag(AccountSet.READONLY);


   public String getEntryType()
   {
      return "bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account";
   }


   @Override
   public AccountSet getNewList(boolean keyValue)
   {
      return new AccountSet();
   }


   public AccountSet filter(Condition<Account> condition) {
      AccountSet filterList = new AccountSet();
      filterItems(filterList, condition);
      return filterList;
   }

   @SuppressWarnings("unchecked")
   public AccountSet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<Account>)value);
      }
      else if (value != null)
      {
         this.add((Account) value);
      }
      
      return this;
   }
   
   public AccountSet without(Account value)
   {
      this.remove(value);
      return this;
   }

   
   //==========================================================================
   
   public AccountSet transfer()
   {
      return AccountSet.EMPTY_SET;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the name attribute values. 
    * 
    * @return List of String objects reachable via name attribute
    */
   public ObjectSet getName()
   {
      ObjectSet result = new ObjectSet();
      
      for (Account obj : this)
      {
         result.add(obj.getName());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the name attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterName(String value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value.equals(obj.getName()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the name attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterName(String lower, String upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower.compareTo(obj.getName()) <= 0 && obj.getName().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the name attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withName(String value)
   {
      for (Account obj : this)
      {
         obj.setName(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the ssn attribute values. 
    * 
    * @return List of int objects reachable via ssn attribute
    */
   public NumberList getSsn()
   {
      NumberList result = new NumberList();
      
      for (Account obj : this)
      {
         result.add(obj.getSsn());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the ssn attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterSsn(int value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getSsn())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the ssn attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterSsn(int lower, int upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower <= obj.getSsn() && obj.getSsn() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the ssn attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withSsn(int value)
   {
      for (Account obj : this)
      {
         obj.setSsn(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the dob attribute values. 
    * 
    * @return List of Object objects reachable via dob attribute
    */
   public ObjectSet getDob()
   {
      ObjectSet result = new ObjectSet();
      
      for (Account obj : this)
      {
         result.add(obj.getDob());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the dob attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterDob(Object value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getDob())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the dob attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withDob(String value)
   {
      for (Account obj : this)
      {
         obj.setDob(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the username attribute values. 
    * 
    * @return List of String objects reachable via username attribute
    */
   public ObjectSet getUsername()
   {
      ObjectSet result = new ObjectSet();
      
      for (Account obj : this)
      {
         result.add(obj.getUsername());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the username attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterUsername(String value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value.equals(obj.getUsername()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the username attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterUsername(String lower, String upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower.compareTo(obj.getUsername()) <= 0 && obj.getUsername().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the username attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withUsername(String value)
   {
      for (Account obj : this)
      {
         obj.setUsername(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the password attribute values. 
    * 
    * @return List of String objects reachable via password attribute
    */
   public ObjectSet getPassword()
   {
      ObjectSet result = new ObjectSet();
      
      for (Account obj : this)
      {
         result.add(obj.getPassword());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the password attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterPassword(String value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value.equals(obj.getPassword()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the password attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterPassword(String lower, String upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower.compareTo(obj.getPassword()) <= 0 && obj.getPassword().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the password attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withPassword(String value)
   {
      for (Account obj : this)
      {
         obj.setPassword(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the initialAmount attribute values. 
    * 
    * @return List of double objects reachable via initialAmount attribute
    */
   public NumberList getInitialAmount()
   {
      NumberList result = new NumberList();
      
      for (Account obj : this)
      {
         result.add(obj.getInitialAmount());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the initialAmount attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterInitialAmount(double value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getInitialAmount())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the initialAmount attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterInitialAmount(double lower, double upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower <= obj.getInitialAmount() && obj.getInitialAmount() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the initialAmount attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withInitialAmount(double value)
   {
      for (Account obj : this)
      {
         obj.setInitialAmount(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the accountBalance attribute values. 
    * 
    * @return List of double objects reachable via accountBalance attribute
    */
   public NumberList getAccountBalance()
   {
      NumberList result = new NumberList();
      
      for (Account obj : this)
      {
         result.add(obj.getAccountBalance());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the accountBalance attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterAccountBalance(double value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getAccountBalance())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the accountBalance attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterAccountBalance(double lower, double upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower <= obj.getAccountBalance() && obj.getAccountBalance() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the accountBalance attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withAccountBalance(double value)
   {
      for (Account obj : this)
      {
         obj.setAccountBalance(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the recentTransaction attribute values. 
    * 
    * @return List of String objects reachable via recentTransaction attribute
    */
   public ObjectSet getRecentTransaction()
   {
      ObjectSet result = new ObjectSet();
      
      for (Account obj : this)
      {
         result.add(obj.getRecentTransaction());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the recentTransaction attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterRecentTransaction(String value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value.equals(obj.getRecentTransaction()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the recentTransaction attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterRecentTransaction(String lower, String upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower.compareTo(obj.getRecentTransaction()) <= 0 && obj.getRecentTransaction().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the recentTransaction attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withRecentTransaction(String value)
   {
      for (Account obj : this)
      {
         obj.setRecentTransaction(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the iOweTheBank attribute values. 
    * 
    * @return List of double objects reachable via iOweTheBank attribute
    */
   public NumberList getIOweTheBank()
   {
      NumberList result = new NumberList();
      
      for (Account obj : this)
      {
         result.add(obj.getIOweTheBank());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the iOweTheBank attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterIOweTheBank(double value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getIOweTheBank())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the iOweTheBank attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterIOweTheBank(double lower, double upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower <= obj.getIOweTheBank() && obj.getIOweTheBank() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the iOweTheBank attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withIOweTheBank(double value)
   {
      for (Account obj : this)
      {
         obj.setIOweTheBank(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Account objects and collect a set of the User objects reached via User_Has. 
    * 
    * @return Set of User objects reachable via User_Has
    */
   public UserSet getUser_Has()
   {
      UserSet result = new UserSet();
      
      for (Account obj : this)
      {
         result.with(obj.getUser_Has());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Account objects and collect all contained objects with reference User_Has pointing to the object passed as parameter. 
    * 
    * @param value The object required as User_Has neighbor of the collected results. 
    * 
    * @return Set of User objects referring to value via User_Has
    */
   public AccountSet filterUser_Has(Object value)
   {
      ObjectSet neighbors = new ObjectSet();

      if (value instanceof Collection)
      {
         neighbors.addAll((Collection<?>) value);
      }
      else
      {
         neighbors.add(value);
      }
      
      AccountSet answer = new AccountSet();
      
      for (Account obj : this)
      {
         if (neighbors.contains(obj.getUser_Has()) || (neighbors.isEmpty() && obj.getUser_Has() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Account object passed as parameter to the User_Has attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their User_Has attributes.
    */
   public AccountSet withUser_Has(User value)
   {
      for (Account obj : this)
      {
         obj.withUser_Has(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Account objects and collect a set of the Bank objects reached via Bank_has. 
    * 
    * @return Set of Bank objects reachable via Bank_has
    */
   public BankSet getBank_has()
   {
      BankSet result = new BankSet();
      
      for (Account obj : this)
      {
         result.with(obj.getBank_has());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Account objects and collect all contained objects with reference Bank_has pointing to the object passed as parameter. 
    * 
    * @param value The object required as Bank_has neighbor of the collected results. 
    * 
    * @return Set of Bank objects referring to value via Bank_has
    */
   public AccountSet filterBank_has(Object value)
   {
      ObjectSet neighbors = new ObjectSet();

      if (value instanceof Collection)
      {
         neighbors.addAll((Collection<?>) value);
      }
      else
      {
         neighbors.add(value);
      }
      
      AccountSet answer = new AccountSet();
      
      for (Account obj : this)
      {
         if (neighbors.contains(obj.getBank_has()) || (neighbors.isEmpty() && obj.getBank_has() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Account object passed as parameter to the Bank_has attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Bank_has attributes.
    */
   public AccountSet withBank_has(Bank value)
   {
      for (Account obj : this)
      {
         obj.withBank_has(value);
      }
      
      return this;
   }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
   
   //==========================================================================
   
   public AccountSet deposit()
   {
      return AccountSet.EMPTY_SET;
   }

   
   //==========================================================================
   
   public NumberList withdraw()
   {
      
      NumberList result = new NumberList();
      
      for (Account obj : this)
      {
         result.add( obj.withdraw() );
      }
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the dob attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterDob(String value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value.equals(obj.getDob()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the dob attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterDob(String lower, String upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower.compareTo(obj.getDob()) <= 0 && obj.getDob().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }

}
