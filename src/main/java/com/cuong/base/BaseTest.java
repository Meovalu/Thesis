package com.cuong.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.cuong.keywords.Keywords;
import com.cuong.utils.ExtentManager;
import com.cuong.utils.LogUtils;

public class BaseTest {

	protected static Properties prop;
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<Keywords> actionDriver = new ThreadLocal<>();

	protected ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);

	// Getter method for soft assert
	public SoftAssert getSoftAssert() {
		return softAssert.get();
	}

	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load the configuration file
		prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/resources/config.properties");
		prop.load(fis);
		LogUtils.info("config.properties file loaded");
	}

	@BeforeMethod
	@Parameters("browser")
	public synchronized void setup(String browser) throws IOException, URISyntaxException {
		System.out.println("Setting up WebDriver for:" + this.getClass().getSimpleName());
		launchBrowser(browser);
		configureBrowser();
		staticWait(2);
		actionDriver.set(new Keywords(getDriver()));
		LogUtils.info("ActionDriver initlialized for thread: " + Thread.currentThread().threadId());

	}

	private synchronized void launchBrowser(String browser) throws URISyntaxException {
		
		boolean seleniumGrid = Boolean.parseBoolean(prop.getProperty("seleniumGrid"));
		boolean headless = Boolean.parseBoolean(prop.getProperty("headless"));
		String gridURL = prop.getProperty("gridURL");
		
		if (seleniumGrid) {
		    try {
		        if (browser.equalsIgnoreCase("chrome")) {
		            ChromeOptions options = new ChromeOptions();
		            options.addArguments("--headless=new", "--disable-gpu","--no-sandbox","--disable-dev-shm-usage");
		            options.addArguments("--window-size=1600,900");
		            driver.set(new RemoteWebDriver(new URI(gridURL).toURL(), options));
		        } else if (browser.equalsIgnoreCase("firefox")) {
		            FirefoxOptions options = new FirefoxOptions();
		            options.addArguments("-headless");
		            options.addArguments("--width=1600");
		            options.addArguments("--height=900");
		            driver.set(new RemoteWebDriver(new URI(gridURL).toURL(), options));
		        } else if (browser.equalsIgnoreCase("edge")) {
		            EdgeOptions options = new EdgeOptions();
		            options.addArguments("--headless=new", "--disable-gpu","--no-sandbox","--disable-dev-shm-usage");
		            options.addArguments("--window-size=1600,900");
		            driver.set(new RemoteWebDriver(new URI(gridURL).toURL(), options));
		        } else {
		            throw new IllegalArgumentException("Browser Not Supported: " + browser);
		        }
		        LogUtils.info("RemoteWebDriver instance created for Grid in headless mode");
		    } catch (MalformedURLException e) {
		        throw new RuntimeException("Invalid Grid URL", e);
		    }
		} else {

		if (browser.equalsIgnoreCase("chrome")) {
			
			// Create ChromeOptions
			ChromeOptions options = new ChromeOptions();

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put("profile.password_manager_leak_detection", false); 
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("autofill.profile_enabled", false); 
            options.setExperimentalOption("prefs", prefs);

            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--window-size=1600,900");

            options.setAcceptInsecureCerts(true);

            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
			// driver = new ChromeDriver();
			driver.set(new ChromeDriver(options)); 
			ExtentManager.registerDriver(getDriver());
			LogUtils.info("ChromeDriver Instance is created.");
		} else if (browser.equalsIgnoreCase("firefox")) {
			
			// Create FirefoxOptions
			FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--width=1600");
            options.addArguments("--height=900");
            options.setAcceptInsecureCerts(true);

            if (headless) {
                options.addArguments("-headless");
            }
			// driver = new FirefoxDriver();
			driver.set(new FirefoxDriver(options)); 
			ExtentManager.registerDriver(getDriver());
			LogUtils.info("FirefoxDriver Instance is created.");
		} else if (browser.equalsIgnoreCase("edge")) {
			
			EdgeOptions options = new EdgeOptions();

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put("profile.password_manager_leak_detection", false); // Turn off change your password
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("autofill.profile_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--window-size=1600,900");
            
            options.setAcceptInsecureCerts(true);

            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
			driver.set(new EdgeDriver(options)); // New Changes as per Thread
			ExtentManager.registerDriver(getDriver());
			LogUtils.info("EdgeDriver Instance is created.");
		} else {
			throw new IllegalArgumentException("Browser Not Supported:" + browser);
		}
		}
	}

	private void configureBrowser() {
		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		boolean seleniumGrid = Boolean.parseBoolean(prop.getProperty("seleniumGrid"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		String env = prop.getProperty("env");

		if (env.equals("test")) {
			if (seleniumGrid) {
				getDriver().get(prop.getProperty("url_grid"));
			} else {
				getDriver().get(prop.getProperty("url_test"));
			}
		}
		else {
			getDriver().get(prop.getProperty("url_local"));
		}
		
	}

	@AfterMethod
	public synchronized void tearDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				System.out.println("unable to quit the driver:" + e.getMessage());
			}
		}
		LogUtils.info("WebDriver instance is closed.");
		driver.remove();
		actionDriver.remove();
		
	}


	// Getter Method for WebDriver
	public static WebDriver getDriver() {

		if (driver.get() == null) {
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver.get();

	}

	// Getter Method for ActionDriver
	public static Keywords getActionDriver() {

		if (actionDriver.get() == null) {
			System.out.println("ActionDriver is not initialized");
			throw new IllegalStateException("ActionDriver is not initialized");
		}
		return actionDriver.get();

	}

	// Getter method for prop
	public static Properties getProp() {
		return prop;
	}

	// Driver setter method
	public void setDriver(ThreadLocal<WebDriver> driver) {
		BaseTest.driver = driver;
	}

	// Static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

}