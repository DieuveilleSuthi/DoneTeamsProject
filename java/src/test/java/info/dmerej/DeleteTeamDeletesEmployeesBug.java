package info.dmerej;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import info.dmerej.Refactoring.*;

public class DeleteTeamDeletesEmployeesBug {
    @Test
    void testDeleteTeamKeepsEmployee() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
            Page page = browser.newContext().newPage();

            HomePage homePage = new HomePage(page);
            EmployeePage employeePage = new EmployeePage(page);
            TeamPage teamPage = new TeamPage(page);

            homePage.navigate();
            homePage.resetDatabase();

            homePage.goToCreateNewTeam();
            teamPage.createNewTeam("Bobteam");

            homePage.navigate();
            homePage.goToAddNewEmployee();
            employeePage.addNewEmployee("Bob", "bob@gmail.com", "20 Bobstreet", "Bobville", "1234", "2025-01-03", "Dev");

            employeePage.assignEmployeeToTeam("Bobteam team");

            homePage.navigate();
            homePage.goToListTeams();
            teamPage.deleteFirstTeam();

            homePage.goToListTeams();
            homePage.resetDatabase();
            assertFalse(teamPage.isTeamVisible("Bobteam"));

            assertTrue(employeePage.isEmployeeVisible("Bob"));

            browser.close();
        }
    }
}
