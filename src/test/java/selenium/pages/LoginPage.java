package selenium.pages;

import io.qameta.allure.Step;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;
import org.openqa.selenium.support.FindBy;

/**
 * Created by MartinRiggs on 6/19/2017.
 */
public class LoginPage extends BasePageClass {

    @FindBy(xpath = ".//*[@id='userid']")
    InputField emailField;

    @FindBy (name = "password")
    InputField passwordField;

    @FindBy(name = "login")
    Button submitButton;

    @FindBy(id = "forgotPassword")
    Button forgotPassBtn;

    @FindBy(id = "forgotEmail")
    InputField forgotEmailField;

    @FindBy(id = "revertToLogin")
    Button returnToLoginBtn;

    @FindBy(name = "forgotPassword")
    Button submitForgotBtn;

    public LoginPage()
    {
        super();
        //setPageUrl("http://192.168.1.214:8080/ui/user-login");
        setPageUrl("/ui/user-login");
    }

    private LoginPage typeEmail(String email)
    {
        emailField.inputText(email);
        return this;
    }

    private LoginPage typePassword(String password)
    {
        passwordField.inputText(password);
        return this;
    }

    @Step("Login by entering email: {email} and password: {password}")
    public LoginPage loginAs(String email, String password)
    {
        openPage();
        typeEmail(email);
        typePassword(password);
        submitButton.click();
        return this;
        // in test validate that some text is present by calling isTextPresent() wrapped by assert function
    }

    public LoginPage clickForgotPasswrdBtn(){
        forgotPassBtn.click();
        return this;
    }

    public LoginPage enterEmailToRestorePasswrd(String forgotEmail){
        forgotEmailField.inputText(forgotEmail);
        return this;
    }

    public LoginPage submitForgot(){
        submitForgotBtn.click();
        return this;
    }


}
