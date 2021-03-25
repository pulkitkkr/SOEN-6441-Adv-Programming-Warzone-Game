package TestSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Models.AdvanceTest;
import Models.AirliftTest;
import Models.BlockadeTest;
import Models.BombTest;
import Models.DeployTest;
import Models.DiplomacyTest;
import Models.PlayerTest;
import Services.PlayerServiceTest;

/**
 * Test suite for testing issue and execution of order functionality and various
 * player services of adding players, assigning armies and countries
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ DeployTest.class, PlayerTest.class, PlayerServiceTest.class, AdvanceTest.class, AirliftTest.class,
		BlockadeTest.class, BombTest.class, DiplomacyTest.class})
public class MainGameTestSuite {
}