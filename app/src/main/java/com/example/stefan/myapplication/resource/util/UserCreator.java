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
   
package com.example.stefan.myapplication.resource.util;

import de.uniks.networkparser.interfaces.SendableEntityCreator;
import com.example.stefan.myapplication.resource.User;
import de.uniks.networkparser.IdMap;
import com.example.stefan.myapplication.resource.Account;
import com.example.stefan.myapplication.resource.Bank;

public class UserCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
   {
      User.PROPERTY_USERNAME,
      User.PROPERTY_ACCOUNT_HAS,
      User.PROPERTY_BANK_HAS,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new User();
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

      if (User.PROPERTY_USERNAME.equalsIgnoreCase(attribute))
      {
         return ((User) target).getUserName();
      }

      if (User.PROPERTY_ACCOUNT_HAS.equalsIgnoreCase(attribute))
      {
         return ((User) target).getAccount_Has();
      }

      if (User.PROPERTY_BANK_HAS.equalsIgnoreCase(attribute))
      {
         return ((User) target).getBank_has();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (User.PROPERTY_USERNAME.equalsIgnoreCase(attrName))
      {
         ((User) target).setUserName((String) value);
         return true;
      }

      if (SendableEntityCreator.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (User.PROPERTY_ACCOUNT_HAS.equalsIgnoreCase(attrName))
      {
         ((User) target).withAccount_Has((Account) value);
         return true;
      }
      
      if ((User.PROPERTY_ACCOUNT_HAS + SendableEntityCreator.REMOVE).equalsIgnoreCase(attrName))
      {
         ((User) target).withoutAccount_Has((Account) value);
         return true;
      }

      if (User.PROPERTY_BANK_HAS.equalsIgnoreCase(attrName))
      {
         ((User) target).setBank_has((Bank) value);
         return true;
      }
      
      return false;
   }
   public static IdMap createIdMap(String sessionID)
   {
      return com.example.stefan.myapplication.resource.util.CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
      public void removeObject(Object entity)
   {
      ((User) entity).removeYou();
   }
}
