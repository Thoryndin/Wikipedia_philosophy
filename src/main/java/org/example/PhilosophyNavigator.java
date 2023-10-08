package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhilosophyNavigator {

    private WebDriver driver;
    private Set<String> visitedLinks;

    public PhilosophyNavigator(WebDriver driver) {
        this.driver = driver;
        visitedLinks = new HashSet<>();
    }

    public void navigateToPhilosophy() {
        driver.get("https://en.wikipedia.org/wiki/Special:Random");
        System.out.println("Starting wiki page: " + driver.getCurrentUrl());
        int redirects = 0;

        while (!driver.getCurrentUrl().contains("Philosophy")) {
            redirects++;
            WebElement firstUniqueLink = findFirstUniqueLink();

            if (firstUniqueLink != null) {
                firstUniqueLink.click();
            } else {
                System.out.println("No more unvisited links left on current page.");
                return;
            }
        }
        System.out.println("Number of redirects to reach Philosophy: " + redirects);
    }

    private WebElement findFirstUniqueLink() {
        List<WebElement> links = driver.findElements(By.cssSelector("#mw-content-text p > a:not(.external):not(.italic):not(table a)"));

        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (!visitedLinks.contains(url) ) {
                System.out.println("Going to url: " + url);
                visitedLinks.add(url);
                return link;
            }
        }
        return null; // No more unique links found
    }

    public static void main(String[] args) {

        // Path to the Chromedriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\kotrc\\Documents\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        PhilosophyNavigator navigator = new PhilosophyNavigator(driver);

        navigator.navigateToPhilosophy();

        driver.quit();
    }
}