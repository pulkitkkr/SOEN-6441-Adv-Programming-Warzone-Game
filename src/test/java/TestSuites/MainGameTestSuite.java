package TestSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Models.OrderTest;
import Models.PlayerTest;
import Services.PlayerServiceTest;

/**
 * Test suite for testing issue and execution of order functionality and
 * various player services of adding players, assigning armies and countries
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ OrderTest.class, PlayerTest.class, PlayerServiceTest.class })
public class MainGameTestSuite {
}