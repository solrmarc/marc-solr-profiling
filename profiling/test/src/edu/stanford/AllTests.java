package edu.stanford;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CallNumLCLoppingUnitTests.class,
        CallNumLoppingUnitTests.class,
        CallNumUtilsLoppingUnitTests.class,
        CollectionTests.class,
        LeaderByteTests.class,
        })

public class AllTests
{
}
