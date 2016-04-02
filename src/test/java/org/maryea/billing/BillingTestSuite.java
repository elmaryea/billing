package org.maryea.billing;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.maryea.billing.content.ContentTestSuite;
import org.maryea.billing.model.ModelTestSuite;
import org.maryea.billing.popups.PopupsTestSuite;
import org.maryea.billing.popups.account.AccountTestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	AccountTestSuite.class,
	ContentTestSuite.class,
	ModelTestSuite.class,
	PopupsTestSuite.class
})
public class BillingTestSuite {
	public static void main(String[] args){
		Result result = JUnitCore.runClasses(BillingTestSuite.class);
		for(Failure failure : result.getFailures()){
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}
}
