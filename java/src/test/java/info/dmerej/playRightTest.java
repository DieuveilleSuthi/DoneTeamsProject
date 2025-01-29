package info.dmerej;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;

public class playRightTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
          Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setHeadless(false));
          BrowserContext context = browser.newContext();
          Page page = context.newPage();
          page.navigate("https://d.se2.hr.dmerej.info/");
          page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
          page.locator("div").click();
          page.getByPlaceholder("Name").fill("addTeamtestName");
          page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
        }
      }
}
