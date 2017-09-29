package selenium.Elements;

import org.openqa.selenium.JavascriptExecutor;
import selenium.BaseElementClass.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.webtestsbase.DriverFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table extends Element {

    public Table(WebElement element) {
        super(element);
    }

    public boolean tableContainsElements(String elementName) {
        /*String source = (String)((JavascriptExecutor) DriverFactory.getInstance().getDriver())
                .executeScript("return arguments[0].innerHTML;", element);
        if (source.toUpperCase().contains(elementName.toUpperCase())){
            return true;
        } else {
            return false;
        }*/
        boolean result = element.findElements(By.xpath("//tr[.//*[contains(text(),'"+ elementName +"')]]")).size() > 0;
        return result;
    }

    public int countAllElementsInTable(){
        /*even if table is empty it has on tr element class="dataTables_empty" colspan="7" valign="top">Empty*/
        List<WebElement> rows = element.findElements(By.xpath("//tbody/tr"));
        return rows.size();
        //TODO maybe xpath count()?
    }

    public int countElementsInTable(String elementName){
        List<WebElement> rows = element.findElements(By.xpath(".//tbody//tr[.//*[(contains(text(),'" + elementName + "'))]]"));
        return rows.size();
    }

    public boolean checkDescendantOrderInTable(String elementName) {
        int columnIndex = getColumnIndex(elementName);
        ArrayList<String> rowList = new ArrayList<String>();
        List<WebElement> elements = element
                .findElements(By.xpath(".//tbody//tr/td["+ columnIndex +"]"));
                //.findElements(By.xpath(".//tbody/tr//*(contains(div,'" + elementName + "'))"));//dependency on user decision
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

    //TODO rewrite cuz its not working completely!!!!!!!!!!!!!!!!
    /*public boolean tableContainsElementsExcept(String elementName) {
        List<WebElement> rows = element.findElements(By.xpath
                ("//tbody//tr[not(contains(text(),'" + elementName + "')) and //tbody//tr[not(contains(text(),'Empty'))]"));
                //".//*[not(contains(text(),'" + elementName + "'))]"));
        //List <WebElement> rows = tableBody.findElements(By.xpath("./*[not(contains(text()," + elementName + "))]"));
        if (rows.isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < rows.size(); i++){
                System.out.println(rows.get(i));
            }
            return true;

        }
    }*/

    public void selectElementCheckboxInTable(String elementName) {
        WebElement searchEl = element.findElement(
                By.xpath("//tr[.//*[contains(text(),'"+ elementName +"')]]//div[@class='checkbox']/label/span/span")); ////span[@class='check']
        searchEl.click();
    }

    public String getCellValueBy(String elementName, String columnName){
        int columnIndex = getColumnIndex(columnName);
        WebElement searchEl = element.findElement(By.xpath(".//tbody//tr[contains(text(),"+ elementName +")]//td["+ columnIndex +"]"));
        return searchEl.getText();
    }

    private int getColumnIndex(String columnName){
        //get list of elements
        List<WebElement> table = element.findElements(By.xpath(".//thead/tr/th"));
        // fill array with elements text which is column name
        ArrayList<String> qwerty = new ArrayList<>();
        for (WebElement list: table)
        {
            qwerty.add(list.getText());
        }
        return qwerty.indexOf(columnName)+1; // add +1 cuz selenium count from not from zero
    }
    public boolean checkAscendantOrderInTable(String elementName)
    {
        int columnIndex = getColumnIndex(elementName);
        ArrayList<String> rowList = new ArrayList<String>();
        List<WebElement> elements = element
                .findElements(By.xpath(".//tbody//tr/td["+ columnIndex +"]"));
                //.findElements(By.xpath(".//tbody/tr//*[(contains(div,'" + elementName + "'))]"));//dependency on user decision
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
        element.findElement(By.xpath(".//thead/tr//*[(contains(div,'" + elementName + "'))]")).click();
    }

    public void selectAllInTable(){
        WebElement searchEl = element
                .findElement(By.xpath(".//thead/tr//th//div[@class='checkbox']/label//span/span[@class='check']"));
        searchEl.click();
    }

    public void clickOnTheLinkBy(String row, String linkName){
        WebElement rowElement = element.findElement(By.xpath("//tbody//tr[td//text()[contains(., '" + row + "')]]"));
        WebElement rowElement1 = rowElement.findElement(By.xpath("//a[contains(text(),'" + linkName + "')]"));
        String[] validate = rowElement1.getAttribute("href").split("/?id=");
        if (validate[1].isEmpty()){
            throw new AssertionError("link is broken. No ID present in link: " + validate[1]);
        }
        rowElement1.click();
    }

    public String getLinkAddressWithValidation(String row, String linkName){
        WebElement rowElement = element.findElement(By.xpath("//tbody//tr[td//text()[contains(., '" + row + "')]]"));
        rowElement = rowElement.findElement(By.xpath("//a[contains(text(),'" + linkName + "')]"));
        String validate = rowElement.getAttribute("href").split("/?id=")[1];
        if (validate.isEmpty()){
            throw new AssertionError("link is broken. No ID present in link: " + validate);
        }
        else {
            return rowElement.getAttribute("href");
        }

    }


}
