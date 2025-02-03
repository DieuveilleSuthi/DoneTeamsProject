package info.dmerej;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class HiringDateTest {
    @Test
    public void setUp() {
        try (Playwright playwright = Playwright.create()) {
            var launchOptions = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000);
            Browser browser = playwright.chromium().launch(launchOptions);
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://d.se2.hr.dmerej.info/");

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
            page.getByPlaceholder("Hiring date").fill("2025-03-03");
            page.getByPlaceholder("Job title").fill("Dev");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();

            // Ajouter l'employé à l'équipe Bobteam
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).last().click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add to team")).click();
            page.getByLabel("Team").selectOption("Bobteam team");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();

            // Vérifier la date d'embauche de l'employé
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).first().click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Update contract")).click();

            assertTrue(page.getByPlaceholder("Hiring date").isVisible());
            String hiringDateStr = page.getByPlaceholder("Hiring date").inputValue();
            LocalDate hiringDate = LocalDate.parse(hiringDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate today = LocalDate.now();

            assertTrue(hiringDate.isBefore(today) || hiringDate.isEqual(today));
            System.out.println("Test réussi : La date d'embauche est valide.");

            browser.close();
        }
    }
}