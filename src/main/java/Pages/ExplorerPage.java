package Pages;

import Utils.reports.ExtentTestManager;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.List;

public class ExplorerPage extends BasePage{
    By loginIcon = new By.ByXPath("//a[@ui-sref='explore']");
    By cardListPlayIcon = new By.ByXPath("//body[1]/div[1]/ui-view[1]/main[1]/div[1]/div[2]/div[1]/ui-view[1]/explore[1]/div[1]/fizy-algoritmic[1]/div[1]/div[2]/fizy-slider[1]/div[1]/div[2]/div[1]/div[1]/div[2]/playlist-card[1]/list-card[1]/div[1]/div[1]/div[1]/div[2]/div[1]/button[1]");
    By themeByListFirsCard = new By.ByXPath("//div[@id='fizy-theme-list-slider']//div[@class='slick-track']//div[@data-slick-index='0']");
    By explorerBtn = new By.ByXPath("//a[contains(text(),'keşfet')]");
    By threeDotBtn = new By.ByXPath("//list-for-you//div[@class='slick-track']//div[3]//div[@class='list-card__header']//button[@class='js-list-context-menu interaction-button__menu']");
    By actionMenuLocator = new By.ByXPath("//ul[@class='context-menu-list context-menu-root']");
    By addListBtn = new By.ByCssSelector(".context-menu-item.test-id-add-to-queue.context-menu-visible");
    By watchIcon = new By.ByXPath("//a[contains(text(),'izle')]");
    By videoPlayIcon = new By.ByXPath("//div[@id='fizy-videos-popular-of-month-slider']//video-card[2]//button[@class='interaction-button__play']");
    By videoLocatorControl = new By.ByXPath("//video[@id='maxi-video-player_Viblast_api']");
    By videoTime = new By.ByXPath("//span[@title='Süre']");
    By maxiPlayerIcon = new By.ByXPath("//button[@title='Büyük Ekran']");
    By profilIcon = new By.ByCssSelector(".js-user-context-menu.user-info__user-menu.ng-scope");
    By logoutBtn = new By.ByCssSelector(".context-menu-item.test-id-logout");
    By fastLoginBtn = new By.ById("webPlayerHeader");



    Actions a = new Actions(drivers());

    public ExplorerPage(WebDriver driver) {
        super(driver);
    }
    public boolean iconIsDisplay(){
        boolean lgnValidation = isDisplay(loginIcon);
        return lgnValidation;
    }

    public void setLoginValidation(){

        Assert.assertTrue(iconIsDisplay());
        System.out.println("Giriş Başarılı");

    }
    public void setThemeSelection(){
        scrollPageElement(drivers()
                .findElement(By
                        .xpath("//fizy-algoritmic[@id='fizy-algoritmic']//h2[@class='ui-section__main-title ng-scope']")));

        Actions actions = new Actions(drivers());
        actions.moveToElement(find(By.xpath("//fizy-algoritmic//div[@id='fizy-domestic-popular-list-slider']//div[@class='slick-track']//div[2]//div[@class='list-card__details']//a"))).perform();
        waitForSecond(2);
        click(cardListPlayIcon);

    }

    public void setListByThemes(){
        click(themeByListFirsCard);
        List<WebElement> themeList = drivers().findElements(By.xpath("//div[@class='list-card__details']"));
        int themeCount = themeList.size();
        if (themeCount==0){
            Assert.fail("Temalara göre listeler boş geldi");
        }
        if (iconIsDisplay()==false){
            drivers().navigate().refresh();
            waitForSecond(3);
            click(explorerBtn);
        }
    }
    public void setActionMenuValidation(){
        a.moveToElement(drivers()
                .findElement(By
                        .xpath("//list-for-you//div[@class='slick-track']//div[3]//div[@class='list-card__details']"))).perform();
        click(threeDotBtn);
        Boolean actionMenuControl = getElementLocatedControl(actionMenuLocator);
        Assert.assertTrue(actionMenuControl);
        waitForSecond(3);
       // click(addListBtn);
       // a.moveToElement(drivers().findElement(By.xpath("//li[@class='context-menu-item test-id-add-to-queue context-menu-visible']"))).doubleClick().perform();
       // drivers().findElement(By.xpath("//li[@class='context-menu-item test-id-add-to-queue context-menu-visible']")).click();
        click(addListBtn);
    }

    public int videoPlayerTimeCount(){
        String getTime = find(videoTime).getText().replace("00:0","");
        return Integer.parseInt(getTime);
    }

    public void setVideoPlayerControl(){
        click(watchIcon);
        waitForSecond(2);
        scrollPageElement(drivers().findElement(By.xpath("//h2[contains(text(),'En Çok İzlenenler')]")));
        a.moveToElement(find(By.xpath("//div[@id='fizy-videos-popular-of-month-slider']//video-card[2]//div[@class='video-card__info']//a[1]"))).perform();
        click(videoPlayIcon);
        waitForSecond(8);
        Boolean videoImageControl = isDisplay(videoLocatorControl);
        Assert.assertTrue(videoImageControl);
        int expectedTime =1;
        if (videoPlayerTimeCount()>=expectedTime){
            ExtentTestManager.getTest().log(Status.PASS,"Video Played");
        }else {
            Assert.fail("Video başlatılmadı");
        }
        click(maxiPlayerIcon);

    }

    public void setLogoutValidation(){

        waitForSecond(2);
        click(profilIcon);
        click(logoutBtn);
        waitForSecond(5);
        Boolean logOutControl = isDisplay(fastLoginBtn);
        Assert.assertTrue(logOutControl);
        ExtentTestManager.getTest().log(Status.PASS,"Log Out is Success");

    }





}
