package info.dmerej.Refactoring;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage {
    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate("https://d.se2.hr.dmerej.info/");
    }

    public void resetDatabase() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Reset database")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
    }

    public void goToListEmployees() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
    }

    public void goToCreateNewTeam() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
    }

    public void goToAddNewEmployee() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
    }

    public void goToListTeams() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List teams")).click();
    }

    public void goToHome() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    }
}
