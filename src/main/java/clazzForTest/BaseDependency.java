package clazzForTest;

import Annotions.CreatedOnTheFly;

import java.util.Date;

public class BaseDependency {
    private Date date = new Date();

    {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Date getDate() {
        return date;
    }
}
