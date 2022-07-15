package Pages;

import Utils.reports.ExtentTestManager;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class SearchBoxResultPage extends BasePage{
    By searchBoxTxt = new By.ByXPath("//translate[@class='ng-scope']");
    By searchBtn = new By.ByXPath("//input[@type='search']");
    By searchIcon = new By.ByXPath("//button[@class='search-area__submit']");
    By firstSongPlaYBtn = new By.ByXPath("//div[@id='fizy-search-results-audio-slider']//div[@class='ng-scope slick-slide slick-current slick-active']//track-list//track-list-item-album[1]//div//div[2]");

    By miniPlayerSongNameTxt = new By.ByXPath("//a[@class='player__media-text ng-binding']");
    By listSongName = new By.ByXPath("//section[@id='fizy-search-results-audio']//div[@class='slick-track']//div[@data-slick-index='0']//track-list-item-album[1]//span[@class='trackList__title ng-binding']");
    By videoPlayBtn = new By.ByXPath("//div[@id='fizy-search-results-video-slider']//div[@data-slick-index='1']//video-list-item[2]//div//div[1]");
    By videoSongNameTxt = new By.ByXPath("//section[@id='fizy-search-results-video']//div[@class='slick-track']//div[@data-slick-index='1']//video-list-item[2]//span[@class='trackList__title ng-binding']");
    By maxiPlayerBtn = new By.ByXPath("//button[@class='js-open-now-playing player__now-playing-open-button']");

    public SearchBoxResultPage(WebDriver driver) {
        super(driver);
    }

    public void setSearchBoxControl(){
        String singerName = "Tarkan";
        waitForSecond(2);
        scrollPageElement(drivers().findElement(By.cssSelector(".nav-search")));
        click(searchBoxTxt);
        waitForSecond(2);
        click(searchBtn);
        send(searchBtn,singerName);
        click(searchIcon);

        for (int i = 1; i <6 ; i++) {
            Boolean titleControl = isDisplay(By.xpath("//section["+i+"]//h2"));
            Assert.assertTrue(titleControl);
        }
        for (int i = 0; i <2 ; i++) {
            String singerNameControl = find(By.xpath("//section[@id='fizy-search-results-audio']//div[@class='slick-track']//div[@data-slick-index='"+i+"']//a[@class='trackList__artist ng-scope']"))
                    .getText();
            Assert.assertEquals(singerNameControl,singerName);

        }
    }

    public String playerSongNameExpected(){
        return find(listSongName).getText();
    }

    public void setSearchBoxSongValidation(){
        click(firstSongPlaYBtn);
        waitForSecond(3);
        String actualSongName = find(miniPlayerSongNameTxt).getText();
        Assert.assertEquals(actualSongName, playerSongNameExpected());
        scrollPageElement(drivers().findElement(By.xpath("//section[5]//h2[@class='ui-section__main-title ng-scope']")));
        waitForSecond(4);
        String videoNameActual = find(videoSongNameTxt).getText();
        click(videoPlayBtn);
        String videoExpectedName = find(miniPlayerSongNameTxt).getText();
        waitForSecond(4);
        Assert.assertEquals(videoNameActual,videoExpectedName);
        ExtentTestManager.getTest().log(Status.PASS, "Listed Song and Playing Song Same");
        click(maxiPlayerBtn);


    }
}
