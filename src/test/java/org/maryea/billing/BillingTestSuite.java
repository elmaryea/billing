package org.maryea.billing;

import org.junit.runner.RunWith;
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

}
