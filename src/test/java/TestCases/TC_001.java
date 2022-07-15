package TestCases;

import Drivers.BaseTest;
import Pages.*;
import net.sourceforge.tess4j.TesseractException;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC_001 extends BaseTest {
    String userName = "5315464799";
    String pass = "Deneme01";


    @Test
    public void fizyLoginTest() throws TesseractException, IOException {
       FastLoginPage fastLoginPage = new FastLoginPage(driver);
        ExplorerPage explorerPage = new ExplorerPage(driver);
        MiniPlayerPage miniPlayerPage = new MiniPlayerPage(driver);
        SearchBoxResultPage searchBoxResultPage = new SearchBoxResultPage(driver);
        MyMusicPage myMusicPage = new MyMusicPage(driver);
       fastLoginPage.fastLogin(userName,pass);

       explorerPage.setLoginValidation();
       explorerPage.setThemeSelection();
       miniPlayerPage.setSongValidation();
       explorerPage.setListByThemes();
       explorerPage.setActionMenuValidation();
       miniPlayerPage.setForwardValidation();
       miniPlayerPage.setBackValidation();
       miniPlayerPage.setPauseValidation();
       miniPlayerPage.setPlaySongValidation();
       miniPlayerPage.setHeartIconValidation();
       miniPlayerPage.setRepeatButtonValidation();
       miniPlayerPage.setMaxiPlayerLaunchValidation();
       miniPlayerPage.setMaxiPlayerPlayListValidation();
       explorerPage.setVideoPlayerControl();
       searchBoxResultPage.setSearchBoxControl();
       searchBoxResultPage.setSearchBoxSongValidation();
       myMusicPage.setCreateNewListControl();
       myMusicPage.setDeleteNewListControl();
       explorerPage.setLogoutValidation();







    }


}
