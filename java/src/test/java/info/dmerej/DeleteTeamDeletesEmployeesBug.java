package info.dmerej;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteTeamDeletesEmployeesBug {
    @Test
    void test_delete_team_keeps_employee() {
        try (Playwright playwright = Playwright.create()) {
            var launchOptions = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000);
            Browser browser = playwright.chromium().launch(launchOptions);
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://d.se2.hr.dmerej.info/");

            // Reset database
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Reset database")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();

            // Ajouter une nouvelle équipe
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
            page.getByPlaceholder("Name").fill("Bobteam");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();

            // Ajouter un nouvel employé
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
            page.getByPlaceholder("Name").fill("Bob");
            page.getByPlaceholder("Email").fill("bob@gmail.com");
            page.locator("#id_address_line1").fill("20 Bobstreet");
            page.getByPlaceholder("City").fill("Bobville");
            page.getByPlaceholder("Zip code").fill("1234");
            page.getByPlaceholder("Hiring date").fill("2025-01-03");
            page.getByPlaceholder("Job title").fill("Dev");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();

            // Ajouter l'employé à l'équipe Bobteam
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).last().click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add to team")).click();
            page.getByLabel("Team").selectOption("Bobteam team");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();

            // Supprimer l'équipe Bobteam
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List teams")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Delete")).first().click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();

            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();



            // Vérifier que l'équipe Bobteam n'existe plus
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List teams")).click();
            boolean teamExists = page.locator("tr:has-text('Bobteam')").isVisible();
            assertFalse(teamExists, "L'équipe Bobteam devrait être supprimée mais elle est encore visible.");

            // Vérifier que l'employé Bob existe toujours
            boolean employeeExists = page.locator("tr:has-text('Bob')").isVisible();
            assertTrue(employeeExists, "L'employé Bob ne devrait pas être supprimé, mais il est introuvable.");

            browser.close();
        }
    }
}
