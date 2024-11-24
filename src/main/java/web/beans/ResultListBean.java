package web.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import web.tables.Result;
import web.utils.ResultService;

import java.io.Serializable;
import java.util.List;

@Named("resultListBean")
@SessionScoped
public class ResultListBean implements Serializable {

    @Inject
    private ResultService resultService;

    public void addResult(Result result) {
        resultService.save(result);
    }

    public List<Result> getResults() {
        return resultService.findAll();
    }

    public void clearResults() {
        resultService.deleteAll();
    }
}