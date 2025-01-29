package info.dmerej;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class HiringDateTest {

    @Test
    public void setUp() {
        // Use playwright driver to get a browser and open a new page
        var playwright = Playwright.create();
        var launchOptions = new BrowserType.LaunchOptions().setHeadless(false)
            .setSlowMo(1000); // Remove this when you're done debugging
        var browser = playwright.chromium().launch(launchOptions);

        // Set base URL for the new context
        var contextOptions = new Browser.NewContextOptions();
        // TODO: fix the URL
        contextOptions.setBaseURL("https://d.se2.hr.dmerej.info");
        var context = browser.newContext(contextOptions);

        var page = context.newPage();
        // Aller à la page d'accueil
        page.navigate("https://d.se2.hr.dmerej.info/");

        // Aller dans la liste des employés et éditer le premier
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).first().click();

        // Aller à la page de mise à jour du contrat
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Update contract")).click();

        // Vérifier que le champ "Hiring date" est visible
        assertTrue(page.getByPlaceholder("Hiring date").isVisible());

        // Récupérer la valeur du champ "Hiring date"
        String hiringDateStr = page.getByPlaceholder("Hiring date").inputValue();

        // Convertir la date en format LocalDate
        LocalDate hiringDate = LocalDate.parse(hiringDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Récupérer la date actuelle
        LocalDate today = LocalDate.now();

        // Vérifier que la date d'embauche n'est pas dans le futur
        assertTrue(hiringDate.isBefore(today) || hiringDate.isEqual(today));

        System.out.println("Test réussi : La date d'embauche est valide.");
    }
}
