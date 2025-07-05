import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class Startup {

    WebDriver driver;

    @BeforeTest
    public void beforeTest(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }
    @AfterTest
    public void AfterTest() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }

    @Test(priority = 0)
    public void homepage(){
        System.out.println("Loading up homepage");
        driver.get("https://blazedemo.com/");
    }
    @Test(priority = 1)
    public void selectRandomDepartureCity() {
        WebElement departureDropdown = driver.findElement(By.name("fromPort"));
        Select select = new Select(departureDropdown);
        List<WebElement> options = select.getOptions();
        Random rand = new Random();
        int randomIndex = rand.nextInt(options.size());

        // Select the random city
        select.selectByIndex(randomIndex);

        // Print selected city
        System.out.println("Selected departure city: " + options.get(randomIndex).getText());
    }
    @Test (priority = 2)
    public void selectRandomDestinationCity() {

        // Destination city
        WebElement toDropdown = driver.findElement(By.name("toPort"));
        Select toSelect = new Select(toDropdown);
        List<WebElement> toOptions = toSelect.getOptions();
        int toIndex = new Random().nextInt(toOptions.size());
        toSelect.selectByIndex(toIndex);
        System.out.println("Selected destination city: " + toOptions.get(toIndex).getText());

    }
    @Test (priority = 3)
    public void SubmitForm(){
        System.out.println("Submitting the form");
        // Submit the form
        driver.findElement(By.cssSelector("input[type='submit']")).click();
    }
    @Test (priority = 4)
    public void chooseRandomFlight() {
        List<WebElement> chooseButtons = driver.findElements(By.cssSelector("input[type='submit'][value='Choose This Flight']"));

        int randomIndex = new Random().nextInt(chooseButtons.size());
        WebElement chosenButton = chooseButtons.get(randomIndex);

        WebElement chosenRow = chosenButton.findElement(By.xpath("./ancestor::tr"));
        System.out.println("Selected flight details: " + chosenRow.getText());
        chosenButton.click();
    }
    @Test (priority = 5)
    public void completePurchaseFormWithRandomData() {
        Random rand = new Random();
        String name = "TestUser" + rand.nextInt(1000);
        driver.findElement(By.id("inputName")).sendKeys(name);
        String address = rand.nextInt(9999) + " Test St";
        driver.findElement(By.id("address")).sendKeys(address);
        String city = "City" + rand.nextInt(100);
        driver.findElement(By.id("city")).sendKeys(city);
        String state = "State" + rand.nextInt(50);
        driver.findElement(By.id("state")).sendKeys(state);
        String zipCode = String.valueOf(10000 + rand.nextInt(89999));
        driver.findElement(By.id("zipCode")).sendKeys(zipCode);
        Select cardTypeSelect = new Select(driver.findElement(By.id("cardType")));
        int cardIndex = rand.nextInt(cardTypeSelect.getOptions().size());
        cardTypeSelect.selectByIndex(cardIndex);
        String selectedCardType = cardTypeSelect.getFirstSelectedOption().getText();
        String cardNumber = String.valueOf(1000000000000000L + rand.nextInt(89999999));
        driver.findElement(By.id("creditCardNumber")).sendKeys(cardNumber);
        String month = String.valueOf(1 + rand.nextInt(12));
        driver.findElement(By.id("creditCardMonth")).clear();
        driver.findElement(By.id("creditCardMonth")).sendKeys(month);
        String year = String.valueOf(2025 + rand.nextInt(5));
        driver.findElement(By.id("creditCardYear")).clear();
        driver.findElement(By.id("creditCardYear")).sendKeys(year);
        String nameOnCard = name + " Card";
        driver.findElement(By.id("nameOnCard")).sendKeys(nameOnCard);
        driver.findElement(By.id("rememberMe")).click();
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        System.out.println("Submitted purchase form with:");
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("City: " + city);
        System.out.println("State: " + state);
        System.out.println("Zip: " + zipCode);
        System.out.println("Card Type: " + selectedCardType);
        System.out.println("Card Number: " + cardNumber);
        System.out.println("Month: " + month);
        System.out.println("Year: " + year);
        System.out.println("Name on Card: " + nameOnCard);
    }
    @Test(priority = 6)
    public void confirmantionTable(){
        WebElement confirmationTable = driver.findElement(By.cssSelector("table.table"));
        List<WebElement> rows = confirmationTable.findElements(By.tagName("tr"));

        System.out.println("==== Purchase Confirmation Details ====");
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() == 2) {
                String key = cells.get(0).getText();
                String value = cells.get(1).getText();
                System.out.println(key + ": " + value);
            }
        }
        System.out.println("=======================================");
    }
}
