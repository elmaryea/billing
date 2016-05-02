package org.maryea.billing.model;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AccountTest.class,
	AddressTest.class,
	BusinessTest.class,
	ChildTest.class
})
public class ModelTestSuite {
	public static void main(String[] args){
		Result result = JUnitCore.runClasses(ModelTestSuite.class);
		for(Failure failure : result.getFailures()){
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}
}
