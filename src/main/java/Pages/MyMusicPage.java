package Pages;

import Utils.reports.ExtentTestManager;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class MyMusicPage extends BasePage{

    By myMusicIcon = new By.ByXPath("//a[@ui-sref='my_music']");
    By createNewListBtn = new By.ByXPath("//div[@class='ui-create-new-block']");
    By listNameTxt = new By.ByXPath("//body/div[2]/div[2]/div[1]/input[1]");
    By okeyBtn = new By.ByXPath("//button[@title='TAMAM']");
    By newListControl = new By.ByXPath("//a[contains(.,'Yeni Liste')]");
    By threeDotBtbn = new By.ByXPath("//div[@class='ui-tab-content']/div[1]//button[@class='js-list-context-menu interaction-button__menu']");
    By deleteBtn = new By.ByXPath("//ul[@class='context-menu-list context-menu-root']//li[3]");
    By popupDeleteBtn = new By.ByCssSelector("button[title='Sil']");



    public MyMusicPage(WebDriver driver) {
        super(driver);
    }

    public void setCreateNewListControl(){
        String listName = "Yeni Liste";
        waitForSecond(2);
        click(myMusicIcon);
        scrollPageElement(drivers().findElement(By.xpath("//div[@class='ui-create-new-block']")));
        click(createNewListBtn);
        send(listNameTxt,listName);
        click(okeyBtn);
        waitForSecond(5);
        drivers().navigate().refresh();
        waitForSecond(5);
        Boolean listControl = isDisplay(newListControl);
        Assert.assertTrue(listControl);
        ExtentTestManager.getTest().log(Status.PASS,"List Created");

    }
    public void setDeleteNewListControl(){
        waitForSecond(6);
        Actions a = new Actions(drivers());
        WebElement nameLocator = drivers().findElement(By.xpath("//a[contains(.,'Yeni Liste')]"));
        a.moveToElement(nameLocator).perform();
        click(threeDotBtbn);
        waitForSecond(2);
        click(deleteBtn);
        waitForSecond(3);
        click(popupDeleteBtn);
        waitForSecond(5);
        Boolean deletedListControl = getElementLocatedControl(By.xpath("//a[contains(.,'Yeni Liste')]"));
        if (deletedListControl==true){
            Assert.fail("Liste Silinmedi");
        }
    }

}
