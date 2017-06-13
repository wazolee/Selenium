package TA.Selenium;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Worker {
	
	private static final String IEService = "D:\\eclipse\\selenum\\IEDriverServer.exe";
	private static final String FFService = "D:\\eclipse\\selenum\\geckodriver.exe";
	private static final String ChService = "D:\\eclipse\\selenum\\chromedriver_.exe";
	private WebDriver driver;
	private WebDriverWait wait;
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(Service service) {
		this.driver = initWebDriver(service);
	} 
	public enum Service{
		IEService, FFService, ChService
	}
	public Worker(Service Service){
		setDriver(Service);
		wait = new WebDriverWait(getDriver(), 5);
	}
	private WebDriver initWebDriver(Service service) {
		WebDriver driver;
		switch (service) {
		case IEService:
			System.setProperty("webdriver.ie.driver", IEService);
			driver = new InternetExplorerDriver();
			break;
		case FFService:
			System.setProperty("webdriver.gecko.driver", FFService);
			driver = new FirefoxDriver();
			break;
		case ChService:
			System.setProperty("webdriver.chrome.driver", ChService);
			driver = new ChromeDriver();
			break;
		default:
			driver = null;
			break;
		}
		return driver;
	}
	
	public void close() {
		driver.close();
	}
	public void quit() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromeDriverServer.exe");
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			Runtime.getRuntime().exec("taskkill /F /IM firefoxdriverServer.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver.quit();
	}
	public void get(String url){
		driver.get(url);
		waitUntil();
	}
	private void waitUntil() {
		boolean b = false;
		do {
			b = ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
//			System.out.println(b);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!b);
//		} while (!((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
    }
	public WebElement findElement(By by){
		waitUntil();
		WebElement element = driver.findElement(by);
		wait.until(ExpectedConditions.visibilityOf(element));
		return element;
	}
	public void LoginWithLocalUser(String userName, String password){
		this.get("http://dekhes7ltsta.ad001.siemens.net/portal");
		this.findElement(By.xpath("//a[text()='Login']")).click();
		// check error table
		// driver.screenshotIfErrorFound();

		// Choosing Local login option, if it is not already chosen
		if ((this.findElement(By.xpath("//input[@id='login_method_local_login']")).isDisplayed())
				&& (this.findElement(By.xpath("//input[@id='login_method_local_login']")).getAttribute("checked")
						.equals("false"))) {
			this.findElement(By.xpath("//input[@id='login_method_local_login']")).click();
		}
		// Entering userName
		WebElement element = this.findElement(By.xpath("//input[@id='username']"));
		element.clear();
		element.sendKeys(userName);
		// type in password
		element = this.findElement(By.xpath("//input[@id='password']"));
		element.clear();
		element.sendKeys(password);
		// click Submit
		element = this.findElement(By.xpath("//input[@name='submit_loginform']"));
		element.click();
		// check error table
		// driver.screenshotIfErrorFound();
	}
	public void Logout(){
		this.get("https://dekhes7ltsta.ad001.siemens.net/portal/login/logout.php");
	}
}
