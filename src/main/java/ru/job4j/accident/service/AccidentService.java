package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;

import java.util.Collection;

@Service
public class AccidentService {
    private final AccidentJdbcTemplate repository;

    public AccidentService(AccidentJdbcTemplate repository) {
        this.repository = repository;
    }

    public Collection<Rule> getRules() {
        return repository.getRules();
    }

    public Collection<AccidentType> getAccidentTypes() {
        return repository.getAccidentTypes();
    }

    public Collection<Accident> getAccidents() {
        return repository.getAccidents();
    }

    public Accident findAccidentById(int id) {
        return repository.findAccidentById(id);
    }

    public void saveOrUpdate(Accident accident, String[] rIds) {
        repository.saveOrUpdate(accident, rIds);
    }
}
