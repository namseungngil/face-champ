package facechamp.api.doc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountApiDocumentation.class, DeviceApiDocumentation.class })
public class ApiDocumentationTestSuite {
}
