package info.dmerej.Refactoring;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TeamPage {
    private final Page page;

    public TeamPage(Page page) {
        this.page = page;
    }

    public void createNewTeam(String teamName) {
        page.getByPlaceholder("Name").fill(teamName);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
    }

    public void deleteFirstTeam() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Delete")).first().click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
    }

    public boolean isTeamVisible(String teamName) {
        return page.locator("tr:has-text('" + teamName + "')").isVisible();
    }
}
