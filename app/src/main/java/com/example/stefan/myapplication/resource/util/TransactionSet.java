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

import de.uniks.networkparser.list.SimpleSet;
import com.example.stefan.myapplication.resource.Transaction;
import de.uniks.networkparser.interfaces.Condition;
import java.util.Collection;
import de.uniks.networkparser.list.StringList;
import de.uniks.networkparser.list.NumberList;

public class TransactionSet extends SimpleSet<Transaction>
{
	protected Class<?> getTypClass() {
		return Transaction.class;
	}

   public TransactionSet()
   {
      // empty
   }

   public TransactionSet(Transaction... objects)
   {
      for (Transaction obj : objects)
      {
         this.add(obj);
      }
   }

   public TransactionSet(Collection<Transaction> objects)
   {
      this.addAll(objects);
   }

   public static final TransactionSet EMPTY_SET = new TransactionSet().withFlag(TransactionSet.READONLY);


   public String getEntryType()
   {
      return "com.example.stefan.myapplication.resource.Transaction";
   }


   @Override
   public TransactionSet getNewList(boolean keyValue)
   {
      return new TransactionSet();
   }


   public TransactionSet filter(Condition<Transaction> condition) {
      TransactionSet filterList = new TransactionSet();
      filterItems(filterList, condition);
      return filterList;
   }

   @SuppressWarnings("unchecked")
   public TransactionSet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<Transaction>)value);
      }
      else if (value != null)
      {
         this.add((Transaction) value);
      }
      
      return this;
   }
   
   public TransactionSet without(Transaction value)
   {
      this.remove(value);
      return this;
   }

   
   //==========================================================================
   
   public StringList logbuilder()
   {
      
      StringList result = new StringList();
      
      for (Transaction obj : this)
      {
         result.add( obj.logbuilder() );
      }
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect a list of the amount attribute values. 
    * 
    * @return List of int objects reachable via amount attribute
    */
   public NumberList getAmount()
   {
      NumberList result = new NumberList();
      
      for (Transaction obj : this)
      {
         result.add(obj.getAmount());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the amount attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterAmount(int value)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (value == obj.getAmount())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the amount attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterAmount(int lower, int upper)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (lower <= obj.getAmount() && obj.getAmount() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and assign value to the amount attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Transaction objects now with new attribute values.
    */
   public TransactionSet withAmount(int value)
   {
      for (Transaction obj : this)
      {
         obj.setAmount(value);
      }
      
      return this;
   }

}
