package info.dmerej;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HiringDateValidityTest {

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            var launchOptions = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000); // Useful for debugging
            Browser browser = playwright.chromium().launch(launchOptions);
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://d.se2.hr.dmerej.info/");

            // Navigate to 'Add new employee' page
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();

            // Fill in the employee details
            page.getByPlaceholder("Name").fill("Bob");
            page.getByPlaceholder("Email").fill("bob@gmail.com");
            page.locator("#id_address_line1").fill("20 Bobstreet");
            page.getByPlaceholder("City").fill("Bobville");
            page.getByPlaceholder("Zip code").fill("1234");

            // Generate a future date dynamically
            LocalDate futureDate = LocalDate.now().plusDays(10); // 10 days into the future
            String formattedDate = futureDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            page.getByPlaceholder("Hiring date").fill(formattedDate);

            page.getByPlaceholder("Job title").fill("Dev");

            // Click the 'Add' button
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();

            // Add assertion to check for error message or form state
            boolean isErrorVisible = page.isVisible("text='Invalid date error message'"); // Modify the selector/text as per your application's response
            System.out.println("Is error visible for future date? " + isErrorVisible);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
