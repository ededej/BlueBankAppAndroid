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
   
package com.example.stefan.myapplication.resource;

import de.uniks.networkparser.interfaces.SendableEntity;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import de.uniks.networkparser.EntityUtil;
import com.example.stefan.myapplication.resource.util.AccountSet;
import com.example.stefan.myapplication.resource.Account;
import com.example.stefan.myapplication.resource.Bank;
   /**
    * 
    * @see <a href='../../../../../../../../../app/src/test/java/bluebankapp/swe443/bluebankappandroid/ExampleUnitTest.java'>ExampleUnitTest.java</a>
 */
   public  class User implements SendableEntity
{

   
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
      setBank_has(null);
      firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_USERNAME = "userName";
   
   private String userName;

   public String getUserName()
   {
      return this.userName;
   }
   
   public void setUserName(String value)
   {
      if ( ! EntityUtil.stringEquals(this.userName, value)) {
      
         String oldValue = this.userName;
         this.userName = value;
         this.firePropertyChange(PROPERTY_USERNAME, oldValue, value);
      }
   }
   
   public User withUserName(String value)
   {
      setUserName(value);
      return this;
   } 


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getUserName());
      return result.substring(1);
   }


   
   /********************************************************************
    * <pre>
    *              one                       many
    * User ----------------------------------- Account
    *              User_Has                   Account_Has
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

   public User withAccount_Has(Account... value)
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
               item.withUser_Has(this);
               firePropertyChange(PROPERTY_ACCOUNT_HAS, null, item);
            }
         }
      }
      return this;
   } 

   public User withoutAccount_Has(Account... value)
   {
      for (Account item : value)
      {
         if ((this.Account_Has != null) && (item != null))
         {
            if (this.Account_Has.remove(item))
            {
               item.setUser_Has(null);
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
    *              many                       one
    * User ----------------------------------- Bank
    *              User_In                   Bank_has
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
            oldValue.withoutUser_In(this);
         }
         
         this.Bank_has = value;
         
         if (value != null)
         {
            value.withUser_In(this);
         }
         
         firePropertyChange(PROPERTY_BANK_HAS, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public User withBank_has(Bank value)
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
}
