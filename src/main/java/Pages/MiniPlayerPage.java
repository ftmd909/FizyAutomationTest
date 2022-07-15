package Pages;

import Utils.reports.ExtentTestManager;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.List;

public class MiniPlayerPage extends BasePage {
    By miniPlayerSongNameTxt = new By.ByXPath("//a[@class='player__media-text ng-binding']");
    By playListIcon = new By.ByXPath("//button[@class='js-open-playlist player__playlist-button']");
    By playListFirstSongNameTxt = new By.ByXPath("//div[@id='play-queue-list']//play-queue-item[1]//span[@class='trackList__title ng-binding']");
    By forwardBtn = new By.ByXPath("//button[@class='js-next player__next-button']");
    By playListSecondSongNameTxt = new By.ByXPath("//div[@id='play-queue-list']//play-queue-item[2]//span[@class='trackList__title ng-binding']");
    By timeLocatorTxt = new By.ByXPath("//span[@title='Süre']");
    By previousBtn = new By.ByCssSelector(".js-previous.player__previous-button");
    By pauseBtn = new By.ByXPath("//button[@title='Duraklat']");
    By playBtn = new By.ById("btn-play");
    By activeHeartIcon = new By.ByXPath("//button[@class='js-like player__like-button is-active']");
    By heartIcon = new By.ByXPath("//button[@class='js-like player__like-button']");
    By heartIconPopup = new By.ByXPath("//div[@class='noty_body']");
    By maxiPlayerImage = new By.ByXPath("//div[@class='now-playing__cover-wrapper']");
    By maxiPlayerIcon = new By.ByXPath("//button[@title='Büyük Ekran']");
    By cleanButton = new By.ByCssSelector(".js-clear-playlist.text-button.ng-scope");
    Actions a = new Actions(drivers());


    public MiniPlayerPage(WebDriver driver) {
        super(driver);
    }

    public void setSongValidation() {
        waitForSecond(3);
        String playerSong = find(miniPlayerSongNameTxt).getText();
        click(playListIcon);
        waitForSecond(2);
        String playListSong = find(playListFirstSongNameTxt).getText();
        Assert.assertEquals(playerSong, playListSong);
        scrollPageElement(drivers().findElement(By.xpath("//theme-list//h2[@class='ui-section__main-title ng-scope']")));

    }

    public void setForwardValidation() {
        waitForSecond(5);
        click(forwardBtn);
        waitForSecond(3);
        String miniPlayerName = find(miniPlayerSongNameTxt).getText();
        click(playListIcon);
        waitForSecond(2);
        String playListName = find(playListSecondSongNameTxt).getText();
        Assert.assertEquals(miniPlayerName, playListName);


    }

    public int timeControl() {
        String timeTxt = find(timeLocatorTxt).getText().replace("00:0", "");
        return Integer.parseInt(timeTxt);
    }

    public void setBackValidation() {
        if (timeControl() >= 3) {
            String playListPreviousSongName = find(playListFirstSongNameTxt).getText();
            WebElement element = drivers().findElement(By.cssSelector(".js-previous.player__previous-button"));
            a.doubleClick(element).perform();
            waitForSecond(2);
            String miniPlayerPreviousSongName = find(miniPlayerSongNameTxt).getText();
            Assert.assertEquals(playListPreviousSongName, miniPlayerPreviousSongName);
        } else {
            String playListPreviousSongName = find(playListFirstSongNameTxt).getText();
            click(previousBtn);
            waitForSecond(2);
            String miniPlayerPreviousSongName = find(miniPlayerSongNameTxt).getText();
            Assert.assertEquals(playListPreviousSongName, miniPlayerPreviousSongName);


        }
    }

    public void setPauseValidation() {
        click(pauseBtn);
        String stopSongTime = find(timeLocatorTxt).getText();
        waitForSecond(4);
        Boolean playBtnControl = isDisplay(playBtn);
        Assert.assertTrue(playBtnControl);
        String afterStopSongTime = find(timeLocatorTxt).getText();
        Assert.assertEquals(stopSongTime, afterStopSongTime);

    }

    public void setPlaySongValidation() {
        String timeActual = find(timeLocatorTxt).getText().replace("00:0", "");
        click(playBtn);
        String timeExpected = find(timeLocatorTxt).getText().replace("00:0", "");
        Assert.assertEquals(timeActual, timeExpected);
        Boolean pauseIconControl = isDisplay(pauseBtn);
        Assert.assertTrue(pauseIconControl);
        ExtentTestManager.getTest().log(Status.PASS, "Song Played");


    }

    public boolean heartBtnSuccess() {
        boolean heartControl = getElementLocatedControl(By.xpath("//button[@class='js-like player__like-button is-active']"));
        return heartControl;
    }

    public void setHeartIconValidation() {
        if (heartBtnSuccess() == true) {
            click(activeHeartIcon);
            waitForSecond(5);
        }
        click(heartIcon);
        Assert.assertTrue(heartBtnSuccess());
        Boolean heartPopup = isDisplay(heartIconPopup);
        Assert.assertTrue(heartPopup);
        ExtentTestManager.getTest().log(Status.PASS, "Heart Icon Clicked");


    }

    public String expectedSongName() {
        return find(miniPlayerSongNameTxt).getText();
    }

    public void setRepeatButtonValidation() {
        String actualSongName = find(miniPlayerSongNameTxt).getText();
        Actions actions = new Actions(drivers());
        WebElement repeatButtonDouble = drivers().findElement(By.xpath("//button[@title='Tekrarla']"));
        actions.doubleClick(repeatButtonDouble).perform();
        WebElement playerClickAndHold = drivers().findElement(By.cssSelector(".player__progress-bar-bg.slider"));
        actions.moveToElement(playerClickAndHold)
                .clickAndHold()
                .moveByOffset(570, 0)
                .release()
                .perform();
        waitForSecond(8);
        Assert.assertEquals(actualSongName, expectedSongName());

    }

    public void setMaxiPlayerLaunchValidation() {
        click(maxiPlayerIcon);
        Boolean maxiPlayerControl = isDisplay(maxiPlayerImage);
        Assert.assertTrue(maxiPlayerControl);


    }
    public int playListGetSize(){
        List<WebElement> playListCount = drivers().findElements(By.xpath("//div[@id='play-queue-list']//play-queue-item"));
        int listCount = playListCount.size();
        return listCount;
    }
    public void setMaxiPlayerPlayListValidation(){
        //click(playListIcon);
        waitForSecond(2);
        click(cleanButton);
        waitForSecond(3);
        int playListCount = 1;
        Assert.assertEquals(playListGetSize(),playListCount);
        click(maxiPlayerIcon);
    }

}