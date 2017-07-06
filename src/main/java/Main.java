import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by agaspari on 7/6/2017.
 */
public class Main {
    private static WebDriver driver;
    static ArrayList<String> visitedPages = new ArrayList<String>();
    public static void main(String[] args) throws IOException {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        driver = new HtmlUnitDriver();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Article: ");
        String page = sc.nextLine();
        getContent(page);
    }

    private static void getContent(String page) throws IOException {
        if(page.equals("Philosophy")){
            System.out.println("Philosophy");
            System.exit(0);
        } else if(visitedPages.contains(page)){
            System.out.println("Repeated page(" + page+ ") - Ending Program");
            System.exit(0);
        }else{
            visitedPages.add(page);
        }

        driver.get("https://en.wikipedia.org/wiki/" + page);
        System.out.println(page);
        String firstParagraph = driver.findElement(By.xpath("//p")).getText();
        String firstParenthesis;
        if(firstParagraph.indexOf('(') > 0 && firstParagraph.indexOf(')') > 0){
            firstParenthesis = firstParagraph.substring(firstParagraph.indexOf('('), firstParagraph.indexOf(')'));
            //System.out.println(firstParenthesis);
        }else{
            firstParenthesis = "";
        }

        List<WebElement> allLinks = driver.findElements(By.xpath("//p/a"));
        for(int i = 0; i < allLinks.size(); i++){
            if(!firstParenthesis.contains(allLinks.get(i).getText())){
                getContent(allLinks.get(i).getAttribute("title").replace(" ", "_"));
            }else{
                continue;
            }
            break;
        }
    }
}
