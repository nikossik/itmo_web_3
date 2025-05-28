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

    @Setter
    @Getter
    private Result result = new Result();

    @Inject
    private ResultListBean resultListBean;

    @Inject
    private PointCounter pointCounter;

    @Inject
    private HitPercentage hitPercentage;

    public void checkHit() {
        result.setHit(isHit());
        resultListBean.addResult(result);

        // Обновление статистики
        pointCounter.addPoint(result);
        hitPercentage.addPoint(result.isHit());
    }

    private boolean isHit() {
        double x = result.getX();
        double y = result.getY();
        double r = result.getR();

        return (x >= 0 && y >= 0 && x <= r && y <= r / 2) || // прямоугольник
               (x <= 0 && y <= 0 && x * x + y * y <= r * r) || // круг
               (x >= 0 && y <= 0 && y >= -r / 2 + x / 2);       // треугольник
    }

    public String clearResults() {
        resultListBean.clearResults();
        pointCounter.reset();
        hitPercentage.reset();
        return "main?faces-redirect=true";
    }
}