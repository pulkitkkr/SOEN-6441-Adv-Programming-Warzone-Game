package TestSuites;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Controllers.GameEngineTest;
import Models.OrderExecutionPhaseTest;
import Utils.CommandTest;
/**
 * Test suite for testing command parsing utility and controller logic to check
 * command validity
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ CommandTest.class, GameEngineTest.class, OrderExecutionPhaseTest.class })
public class CommandControllerTestSuite {   
}  