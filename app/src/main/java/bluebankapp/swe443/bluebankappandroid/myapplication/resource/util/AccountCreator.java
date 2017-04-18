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

import de.uniks.networkparser.interfaces.SendableEntityCreator;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Account;
import de.uniks.networkparser.IdMap;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.User;
import bluebankapp.swe443.bluebankappandroid.myapplication.resource.Bank;

public class AccountCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
   {
      Account.PROPERTY_NAME,
      Account.PROPERTY_SSN,
      Account.PROPERTY_DOB,
      Account.PROPERTY_USERNAME,
      Account.PROPERTY_PASSWORD,
      Account.PROPERTY_INITIALAMOUNT,
      Account.PROPERTY_ACCOUNTBALANCE,
      Account.PROPERTY_RECENTTRANSACTION,
      Account.PROPERTY_IOWETHEBANK,
      Account.PROPERTY_USER_HAS,
      Account.PROPERTY_BANK_HAS,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Account();
   }
   
   @Override
   public Object getValue(Object target, String attrName)
   {
      int pos = attrName.indexOf('.');
      String attribute = attrName;
      
      if (pos > 0)
      {
         attribute = attrName.substring(0, pos);
      }

      if (Account.PROPERTY_NAME.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getName();
      }

      if (Account.PROPERTY_SSN.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getSsn();
      }

      if (Account.PROPERTY_DOB.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getDob();
      }

      if (Account.PROPERTY_USERNAME.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getUsername();
      }

      if (Account.PROPERTY_PASSWORD.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getPassword();
      }

      if (Account.PROPERTY_INITIALAMOUNT.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getInitialAmount();
      }

      if (Account.PROPERTY_ACCOUNTBALANCE.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getAccountBalance();
      }

      if (Account.PROPERTY_RECENTTRANSACTION.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getRecentTransaction();
      }

      if (Account.PROPERTY_IOWETHEBANK.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getIOweTheBank();
      }

      if (Account.PROPERTY_USER_HAS.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getUser_Has();
      }

      if (Account.PROPERTY_BANK_HAS.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getBank_has();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (Account.PROPERTY_IOWETHEBANK.equalsIgnoreCase(attrName))
      {
         ((Account) target).setIOweTheBank(Double.parseDouble(value.toString()));
         return true;
      }

      if (Account.PROPERTY_RECENTTRANSACTION.equalsIgnoreCase(attrName))
      {
         ((Account) target).setRecentTransaction((String) value);
         return true;
      }

      if (Account.PROPERTY_ACCOUNTBALANCE.equalsIgnoreCase(attrName))
      {
         ((Account) target).setAccountBalance(Double.parseDouble(value.toString()));
         return true;
      }

      if (Account.PROPERTY_INITIALAMOUNT.equalsIgnoreCase(attrName))
      {
         ((Account) target).setInitialAmount(Double.parseDouble(value.toString()));
         return true;
      }

      if (Account.PROPERTY_PASSWORD.equalsIgnoreCase(attrName))
      {
         ((Account) target).setPassword((String) value);
         return true;
      }

      if (Account.PROPERTY_USERNAME.equalsIgnoreCase(attrName))
      {
         ((Account) target).setUsername((String) value);
         return true;
      }

      if (Account.PROPERTY_DOB.equalsIgnoreCase(attrName))
      {
         ((Account) target).setDob((String) value);
         return true;
      }

      if (Account.PROPERTY_SSN.equalsIgnoreCase(attrName))
      {
         ((Account) target).setSsn(Integer.parseInt(value.toString()));
         return true;
      }

      if (Account.PROPERTY_NAME.equalsIgnoreCase(attrName))
      {
         ((Account) target).setName((String) value);
         return true;
      }

      if (SendableEntityCreator.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (Account.PROPERTY_USER_HAS.equalsIgnoreCase(attrName))
      {
         ((Account) target).setUser_Has((User) value);
         return true;
      }

      if (Account.PROPERTY_BANK_HAS.equalsIgnoreCase(attrName))
      {
         ((Account) target).setBank_has((Bank) value);
         return true;
      }
      
      return false;
   }
   public static IdMap createIdMap(String sessionID)
   {
      return bluebankapp.swe443.bluebankappandroid.myapplication.resource.util.CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
      public void removeObject(Object entity)
   {
      ((Account) entity).removeYou();
   }
}
