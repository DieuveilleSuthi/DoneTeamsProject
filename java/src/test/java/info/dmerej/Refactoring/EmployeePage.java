package info.dmerej.Refactoring;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class EmployeePage {
    private final Page page;

    public EmployeePage(Page page) {
        this.page = page;
    }

    public void addNewEmployee(String name, String email, String address, String city, String zip, String hiringDate, String jobTitle) {
        page.getByPlaceholder("Name").fill(name);
        page.getByPlaceholder("Email").fill(email);
        page.locator("#id_address_line1").fill(address);
        page.getByPlaceholder("City").fill(city);
        page.getByPlaceholder("Zip code").fill(zip);
        page.getByPlaceholder("Hiring date").fill(hiringDate);
        page.getByPlaceholder("Job title").fill(jobTitle);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
    }

    public void assignEmployeeToTeam(String teamName) {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).last().click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add to team")).click();
        page.getByLabel("Team").selectOption(teamName);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
    }

    public boolean isEmployeeVisible(String employeeName) {
        return page.locator("tr:has-text('" + employeeName + "')").isVisible();
    }
}
