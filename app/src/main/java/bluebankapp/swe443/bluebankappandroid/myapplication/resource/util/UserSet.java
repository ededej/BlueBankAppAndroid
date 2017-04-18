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

import de.uniks.networkparser.list.SimpleSet;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;
import de.uniks.networkparser.interfaces.Condition;
import java.util.Collection;
import de.uniks.networkparser.list.ObjectSet;
import java.util.Collections;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.util.AccountSet;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.util.BankSet;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;

public class UserSet extends SimpleSet<User>
{
	protected Class<?> getTypClass() {
		return User.class;
	}

   public UserSet()
   {
      // empty
   }

   public UserSet(User... objects)
   {
      for (User obj : objects)
      {
         this.add(obj);
      }
   }

   public UserSet(Collection<User> objects)
   {
      this.addAll(objects);
   }

   public static final UserSet EMPTY_SET = new UserSet().withFlag(UserSet.READONLY);


   public String getEntryType()
   {
      return "bluebankapp.swe443.bluebankappandroid.myapplication.resource.User";
   }


   @Override
   public UserSet getNewList(boolean keyValue)
   {
      return new UserSet();
   }


   public UserSet filter(Condition<User> condition) {
      UserSet filterList = new UserSet();
      filterItems(filterList, condition);
      return filterList;
   }

   @SuppressWarnings("unchecked")
   public UserSet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<User>)value);
      }
      else if (value != null)
      {
         this.add((User) value);
      }
      
      return this;
   }
   
   public UserSet without(User value)
   {
      this.remove(value);
      return this;
   }


   /**
    * Loop through the current set of User objects and collect a list of the userName attribute values. 
    * 
    * @return List of String objects reachable via userName attribute
    */
   public ObjectSet getUserName()
   {
      ObjectSet result = new ObjectSet();
      
      for (User obj : this)
      {
         result.add(obj.getUserName());
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and collect those User objects where the userName attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of User objects that match the parameter
    */
   public UserSet filterUserName(String value)
   {
      UserSet result = new UserSet();
      
      for (User obj : this)
      {
         if (value.equals(obj.getUserName()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and collect those User objects where the userName attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of User objects that match the parameter
    */
   public UserSet filterUserName(String lower, String upper)
   {
      UserSet result = new UserSet();
      
      for (User obj : this)
      {
         if (lower.compareTo(obj.getUserName()) <= 0 && obj.getUserName().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and assign value to the userName attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of User objects now with new attribute values.
    */
   public UserSet withUserName(String value)
   {
      for (User obj : this)
      {
         obj.setUserName(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of User objects and collect a set of the Account objects reached via Account_Has. 
    * 
    * @return Set of Account objects reachable via Account_Has
    */
   public AccountSet getAccount_Has()
   {
      AccountSet result = new AccountSet();
      
      for (User obj : this)
      {
         result.with(obj.getAccount_Has());
      }
      
      return result;
   }

   /**
    * Loop through the current set of User objects and collect all contained objects with reference Account_Has pointing to the object passed as parameter. 
    * 
    * @param value The object required as Account_Has neighbor of the collected results. 
    * 
    * @return Set of Account objects referring to value via Account_Has
    */
   public UserSet filterAccount_Has(Object value)
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
      
      UserSet answer = new UserSet();
      
      for (User obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getAccount_Has()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the User object passed as parameter to the Account_Has attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Account_Has attributes.
    */
   public UserSet withAccount_Has(Account value)
   {
      for (User obj : this)
      {
         obj.withAccount_Has(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the User object passed as parameter from the Account_Has attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public UserSet withoutAccount_Has(Account value)
   {
      for (User obj : this)
      {
         obj.withoutAccount_Has(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of User objects and collect a set of the Bank objects reached via Bank_has. 
    * 
    * @return Set of Bank objects reachable via Bank_has
    */
   public BankSet getBank_has()
   {
      BankSet result = new BankSet();
      
      for (User obj : this)
      {
         result.with(obj.getBank_has());
      }
      
      return result;
   }

   /**
    * Loop through the current set of User objects and collect all contained objects with reference Bank_has pointing to the object passed as parameter. 
    * 
    * @param value The object required as Bank_has neighbor of the collected results. 
    * 
    * @return Set of Bank objects referring to value via Bank_has
    */
   public UserSet filterBank_has(Object value)
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
      
      UserSet answer = new UserSet();
      
      for (User obj : this)
      {
         if (neighbors.contains(obj.getBank_has()) || (neighbors.isEmpty() && obj.getBank_has() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the User object passed as parameter to the Bank_has attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Bank_has attributes.
    */
   public UserSet withBank_has(Bank value)
   {
      for (User obj : this)
      {
         obj.withBank_has(value);
      }
      
      return this;
   }

}
