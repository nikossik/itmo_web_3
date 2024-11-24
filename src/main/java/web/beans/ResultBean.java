package web.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import web.tables.Result;

import java.io.Serializable;

@Named("resultBean")
@SessionScoped
public class ResultBean implements Serializable {

    @Getter
    @Setter
    private Result result = new Result();

    @Inject
    private ResultListBean resultListBean;

    public void checkHit() {
        result.setHit(checkPoint());
        saveResult();
    }

    private void saveResult() {
        resultListBean.addResult(result);
    }

    private boolean checkPoint() {
        double x = result.getX();
        double y = result.getY();
        double r = result.getR();

        return (x >= 0 && y >= 0 && x <= r && y <= r / 2) ||
                (x <= 0 && y <= 0 && (x * x + y * y <= r * r)) ||
                (x >= 0 && y <= 0 && y >= -r/2 + x/2);
    }

    public String clearResults() {
        resultListBean.clearResults();
        return "main?faces-redirect=true";
    }
}