package selenium.pages;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Table;
import selenium.webtestbase.BasePageClass;

public class JobRunsPage extends BasePageClass {

    @FindBy(id = "tbl-jobruns-current")
    Table jobRunsInProgressTable;

    @FindBy(id = "tbl-jobruns_wrapper")
    Table jobRunsHistoryTable;

    public JobRunsPage(){
        super();
        setPageUrl("/ui/jobruns");
    }

    public Table getJobRunsInProgressTable() {
        return jobRunsInProgressTable;
    }

    public Table getJobRunsHistoryTable() {
        return jobRunsHistoryTable;
    }
}
