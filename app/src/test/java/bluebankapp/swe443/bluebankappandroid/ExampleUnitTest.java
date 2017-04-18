package bluebankapp.swe443.bluebankappandroid;

import org.junit.Test;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.models.classes.Feature;
import org.sdmlib.storyboards.Storyboard;

import de.uniks.networkparser.graph.Cardinality;
import de.uniks.networkparser.graph.Clazz;
import de.uniks.networkparser.graph.DataType;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
      /**
    * 
    * @see <a href='../../../../../../../doc/Android.html'>Android.html</a>
 */
   @Test
    public void testAndroid() {
        ClassModel model = new ClassModel("bluebankapp.swe443.bluebankappandroid.myapplication.resource");

        Clazz accountClass = model.createClazz("Account")
                .withAttribute("name", DataType.STRING)
                .withAttribute("ssn", DataType.INT)
                .withAttribute("dob", DataType.STRING)
                .withAttribute("username", DataType.STRING)
                .withAttribute("password", DataType.STRING)
                .withAttribute("initialAmount", DataType.DOUBLE)
                .withAttribute("accountBalance", DataType.DOUBLE)
                .withAttribute("recentTransaction",DataType.STRING)
                .withAttribute("iOweTheBank",DataType.DOUBLE)
                //.withMethod("deposit", DataType.VOID)
                //.withMethod("withdraw",DataType.DOUBLE)
                //.withMethod("undoRecentTransaction", DataType.VOID)
                .withMethod("transfer", DataType.VOID);

        Clazz bankClass = model.createClazz("Bank")
                .withAttribute("bankName", DataType.STRING)
                .withMethod("mainMenu", DataType.OBJECT)
                .withMethod("createAccount", DataType.VOID)
                .withMethod("logIn", DataType.VOID)
                .withMethod("makeDeposit", DataType.VOID)
                .withMethod("makeWithdrawal", DataType.VOID)
                .withMethod("makeTransfer", DataType.VOID)
                .withMethod("undoMostRecentTransaction",DataType.VOID)
                .withMethod("viewBalance", DataType.VOID);

        Clazz transactionClass = model.createClazz("Transaction")
                .withAttribute("amount", DataType.INT)
                .withMethod("logbuilder",DataType.STRING);


        Clazz userClass = model.createClazz("User")
                .withAttribute("userName", DataType.STRING);

        //bank has MANY user & user has ONE banks
        bankClass.withBidirectional(userClass, "User_In", Cardinality.MANY, "Bank_has", Cardinality.ONE);

        //user has MANY accounts & account has ONE user
        userClass.withBidirectional(accountClass, "Account_Has", Cardinality.MANY, "User_Has", Cardinality.ONE);

        //account belongs to ONE bank & bank can have MANY accounts
        accountClass.withBidirectional(bankClass, "Bank_has", Cardinality.ONE, "Account_Has", Cardinality.MANY);



        model.withoutFeature(Feature.PATTERNOBJECT);

        model.generate("app/src/main/java");

        Storyboard storyboard = new Storyboard();
        storyboard.add("Bank Storyboard");
        storyboard.addClassDiagram(model);
        storyboard.dumpHTML();
    }
}
