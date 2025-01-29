package info.dmerej;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class DeleteTeamDeletesEmployeesBug {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            var launchOptions = new BrowserType.LaunchOptions().setHeadless(false)
                    .setSlowMo(1000); // Remove this when you're done debugging
            Browser browser = playwright.chromium().launch(launchOptions);
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://d.se2.hr.dmerej.info/");

            // Add a new team
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
            page.getByPlaceholder("Name").fill("Bobteam");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();

            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();


            // Add a new employee
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
            page.getByPlaceholder("Name").fill("Bob");
            page.getByPlaceholder("Email").fill("bob@gmail.com");
            page.locator("#id_address_line1").fill("20 Bobstreet");
            page.getByPlaceholder("City").fill("Bobville");
            page.getByPlaceholder("Zip code").fill("1234");
            page.getByPlaceholder("Hiring date").fill("2025-01-03");
            page.getByPlaceholder("Job title").fill("Dev");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();

            // Add the new employee to the team
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).last().click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add to team")).click();
            page.getByLabel("Team").selectOption("Bobteam team");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();

            // Delete that team
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List teams")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Delete")).first().click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();

            // Check if the employee is deleted
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();

            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List teams")).click();


        }
    }
}