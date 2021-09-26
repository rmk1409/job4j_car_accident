package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public Accident save(Accident accident) {
        try (Session session = sf.openSession()) {
            session.save(accident);
            return accident;
        }
    }

    public List<Accident> getAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Accident", Accident.class)
                    .list();
        }
    }

    public Collection<Rule> getRules() {
        return tx(session -> session
                .createQuery("from Rule", Rule.class)
                .list());
    }

    public Collection<Accident> getAccidents() {
        return tx(session -> session
                .createQuery("select distinct a from Accident a join fetch a.rules join fetch a.type", Accident.class)
                .list());
    }

    public Collection<AccidentType> getAccidentTypes() {
        return tx(session -> session
                .createQuery("from AccidentType", AccidentType.class)
                .list());
    }

    public Accident findAccidentById(int id) {
        return tx(session -> session
                .createQuery("select distinct a from Accident a join fetch a.rules join fetch a.type where a.id = :id", Accident.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    public void saveOrUpdate(Accident accident, String[] rIds) {
        tx(session -> {
            for (String id : rIds) {
                accident.addRule(session.find(Rule.class, Integer.parseInt(id)));
            }
            return session.save(accident);
        });
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
