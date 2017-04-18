package com.example.stefan.myapplication.resource.util;

import de.uniks.networkparser.IdMap;

class CreatorCreator{

   public static IdMap createIdMap(String sessionID)
   {
      IdMap jsonIdMap = new IdMap().withSessionId(sessionID);
      jsonIdMap.with(new AccountCreator());
      jsonIdMap.with(new BankCreator());
      jsonIdMap.with(new TransactionCreator());
      jsonIdMap.with(new UserCreator());
      return jsonIdMap;
   }
}
