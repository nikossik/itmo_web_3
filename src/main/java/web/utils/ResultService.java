package web.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import web.tables.Result;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class ResultService implements Serializable {

    @PersistenceContext(unitName = "StudsPU")
    private EntityManager manager;

    @Transactional
    public void save(Result result) {
        manager.persist(result);
    }

    @Transactional
    public List<Result> findAll() {
        return manager.createQuery("SELECT r FROM Result r", Result.class).getResultList();
    }

    @Transactional
    public void deleteAll() {
        manager.createQuery("DELETE FROM Result").executeUpdate();
    }
}