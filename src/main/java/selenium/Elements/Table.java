package selenium.Elements;

import selenium.BaseElementClass.Element;
import selenium.webtestsbase.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table extends Element {

    public Table(WebElement element) {
        super(element);
    }

    public boolean tableContainsElements(String elementName)
    {
        String source = (String)((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("return arguments[0].innerHTML;", element);
        if (source.toUpperCase().contains(elementName.toUpperCase()))
            return true;
        else
            return false;
    }

    public int countAllElementsInTable(){
        List<WebElement> rows = element.findElements(By.xpath("//tbody/tr"));
        return rows.size();
    }

    public int countElementsInTable(String elementName){
        List<WebElement> rows = element.findElements(By.xpath("//*[(contains(text(),'" + elementName + "'))]"));
        return rows.size();
    }

    public boolean checkDescendantOrderInTable(String elementName)
    {
        ArrayList<String> rowList = new ArrayList<String>();
        List<WebElement> elements = DriverFactory.getDriver()
                .findElements(By.xpath("//thead/th//*(contains(div,'" + elementName + "'))"));//dependency on user decision
        for (WebElement list: elements)
        {
            rowList.add(list.getText());
        }
        ArrayList<String> bufList = new ArrayList<String>();
        bufList.addAll(rowList);
        /*for (String strArr: rowList)
        {
            bufList.add(strArr);
        }*/
        Collections.sort(bufList);
        Collections.reverse(bufList);
        if (bufList.equals(rowList))
            return true;
        else
            return false;
    }

    public boolean tableContainsElementsExcept(String elementName) {
        List<WebElement> rows = element.findElements(By.xpath("//*[not(contains(td,'" + elementName + "'))]"));
        //List <WebElement> rows = tableBody.findElements(By.xpath("./*[not(contains(text()," + elementName + "))]"));
        if (rows.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void selectElementCheckboxInTable(String elementName)
    {
        WebElement searchEl = element.findElement(
                By.xpath("//tr[.//*[contains(text(),'"+ elementName +"')]]//span[@class='check']"));
        searchEl.click();
    }

    public boolean checkAscendantOrderInTable(String elementName)
    {
        ArrayList<String> rowList = new ArrayList<String>();
        List<WebElement> elements = DriverFactory.getDriver()
                .findElements(By.xpath("//thead/th//*[(contains(div,'" + elementName + "'))]"));//dependency on user decision
        for (WebElement list: elements)
        {
            rowList.add(list.getText());
        }
        ArrayList<String> bufList = new ArrayList<String>();
        bufList.addAll(rowList);
        /*for (String strArr: rowList)
        {
            bufList.add(strArr);
        }*/
        Collections.sort(bufList);
        if (bufList.equals(rowList))
            return true;
        else
            return false;
    }

    public void sortBy(String elementName){
        DriverFactory.getDriver().findElement(By.xpath("//thead/tr//*[(contains(div,'" + elementName + "'))]")).click();
    }

    public void selectAllInTable(){
        WebElement searchEl = element.findElement(By.xpath("//[@id='cb-checkall']//span/span"));
        searchEl.click();
    }


}
