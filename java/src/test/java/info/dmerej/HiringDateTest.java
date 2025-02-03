package info.dmerej;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import info.dmerej.Refactoring.*;

public class HiringDateTest {
    @Test
    public void testHiringDateValidation() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
            Page page = browser.newContext().newPage();

            HomePage homePage = new HomePage(page);
            EmployeePage employeePage = new EmployeePage(page);
            TeamPage teamPage = new TeamPage(page);

            homePage.navigate();
            homePage.resetDatabase();
            homePage.navigate();
            homePage.goToCreateNewTeam();
            teamPage.createNewTeam("Bobteam");
            homePage.navigate();

            homePage.goToAddNewEmployee();
            employeePage.addNewEmployee("Bob", "bob@gmail.com", "20 Bobstreet", "Bobville", "1234", "2025-03-03", "Dev");

            employeePage.assignEmployeeToTeam("Bobteam team");
            homePage.navigate();

            homePage.goToListEmployees();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).first().click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Update contract")).click();

            assertTrue(page.getByPlaceholder("Hiring date").isVisible());
            String hiringDateStr = page.getByPlaceholder("Hiring date").inputValue();
            LocalDate hiringDate = LocalDate.parse(hiringDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate today = LocalDate.now();

            assertTrue(hiringDate.isBefore(today) || hiringDate.isEqual(today));

            homePage.navigate();
            homePage.resetDatabase();

            browser.close();
        }
    }
}
