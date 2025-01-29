package info.dmerej;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class UpdateJobTitle{
    @Test
    void test_update_job_title(){
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


        // Add user
        // name
        page.navigate("/add_employee");
        var nameInput = page.locator("input[name=\"name\"]");
        var userName = "aze";
        nameInput.fill(userName);
        // mail
        var mailInput = page.locator("input[name=\"email\"]");
        var emailName = "aze@test.com";
        mailInput.fill(emailName);
        // adress line 1
        var adress1Input = page.locator("input[name=\"adress_line1\"]");
        var adress1Name = "22 rue des treza";
        adress1Input.fill(adress1Name);
        // adress line 2
        var adress2Input = page.locator("input[name=\"adress_line2\"]");
        var adress2Name = "22 rue des azs";
        adress2Input.fill(adress2Name);
        // city
        var cityInput = page.locator("input[name=\"city\"]");
        var cityName = "Test City";
        cityInput.fill(cityName);
        // ZIP code
        var ZIPInput = page.locator("input[name=\"zip_code\"]");
        var ZIPName = "12345";
        ZIPInput.fill(ZIPName);
        // hiring date
        var hiringInput = page.locator("input[name=\"hiring_date\"]");
        var hiringName = "12/12/1980";
        hiringInput.fill(hiringName);
        // job title
        var jobInput = page.locator("input[name=\"job_title\"]");
        var jobName = "Dev";
        jobInput.fill(jobName);

        page.click("text='Add'");

        // Check that the employee has been added
        page.navigate("/employees");

        // Check the new employee is there
        String selector = String.format("td:has-text('%s')", userName);
        var isVisible = page.isVisible(selector);
        assertTrue(isVisible);

        // Edit employee
        page.click("text='Edit'");
        page.navigate("/employee/14");

        // change job title
        page.click("text='Update contract'");
        page.navigate("/employee/14/contract")
    }
}