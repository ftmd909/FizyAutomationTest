package Pages;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

public class FastLoginPage extends BasePage{
    By fastLoginBtn = new By.ById("webPlayerHeader");
    By phoneTxt = new By.ById("phoneNo");
    By fastCheckBox = new By.ByXPath("//label[@for='loginWithPassword']");
    By loginBtn = new By.ById("webLogin-button");
    By passwordTxt = new By.ById("password");
    By captchaControlOne = new By.ById("captchaSrc");
    By okBtn = new By.ById("password-login-forward-button");
    By captchaCodeTxt = new By.ById("captcha-code");
    By errorOkBtn = new By.ById("generic-popup-ok-button");
    By repeatBtn = new By.ByXPath("//a[@class='m-captcha__return']");

    public FastLoginPage(WebDriver driver) {
        super(driver);
    }
    public String imageRead() throws IOException, TesseractException {
        waitForSecond(2);
        WebElement imageC = drivers().findElement(By.id("captchaSrc"));
        File src = imageC.getScreenshotAs(OutputType.FILE);
        String path = "C:\\Users\\siram\\Desktop\\FizyAutomationTest\\captchaImage\\captcha.png";
        String testPath = "C:\\Users\\siram\\Desktop\\FizyAutomationTest\\tessdata";
        FileHandler.copy(src,new File(path));
        waitForSecond(2);
        ITesseract tes = new Tesseract();
        tes.setDatapath(testPath);
        String str = tes.doOCR(new File(path));
        String newCode = str.replaceAll("[^\\d.]","");
        return newCode;

    }
    public boolean captchaControlError (){
        boolean errorMessage = isDisplay(errorOkBtn);
        return errorMessage;

    }

    public void fastLogin(String username, String password) throws TesseractException, IOException {
        click(fastLoginBtn);
        String orginalWindow = drivers().getWindowHandle();
        assert drivers().getWindowHandles().size() ==1;
        waitForSecond(2);
        for (String windowHandle : drivers().getWindowHandles()){
            if (!orginalWindow.contentEquals(windowHandle)){
                drivers().switchTo().window(windowHandle);
                send(phoneTxt,username);
                click(fastCheckBox);
                click(loginBtn);
                Boolean captchaControl = getElementLocatedControl(captchaControlOne);
                if (captchaControl==true){
                   while (captchaControlError()==false){
                       String captchaText = imageRead();
                        while (captchaText.length()!=6){
                            click(repeatBtn);
                            waitForSecond(2);
                            captchaText = imageRead();
                        }
                        send(captchaCodeTxt,captchaText);
                        click(loginBtn);
                        boolean errorMessageControl = isDisplay(errorOkBtn);
                        if (errorMessageControl==true){
                            click(errorOkBtn);
                            click(repeatBtn);

                        }else {
                            break;
                        }
                   }
                    send(passwordTxt,password);
                    click(okBtn);
                }else {
                    send(passwordTxt,password);
                    click(okBtn);
                }

                break;
            }
        }
        drivers().switchTo().window(orginalWindow);
    }




}
