package ru.job4j.accident.repository;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;
    private final String findAccidentsQuery = "" +
            "select a.id acc_id, a.name acc_name, t.name type_name, text, address, type_id, r.id r_id, r.name r_name " +
            "from accident a " +
            "inner join accident_type t on a.type_id = t.id " +
            "inner join accident_rule ar on a.id = ar.accident_id " +
            "inner join rule r on ar.rule_id = r.id ";

    private final Map<Integer, Accident> idToAccident = new HashMap<>();
    private final RowMapper<Accident> mapper = (rs, row) -> {
        int accId = rs.getInt("acc_id");
        idToAccident.computeIfAbsent(accId, integer -> {
            Accident accident = new Accident();
            accident.setId(accId);
            try {
                accident.setName(rs.getString("acc_name"));
                accident.setText(rs.getString("text"));
                accident.setAddress(rs.getString("address"));
                accident.setType(AccidentType.of(rs.getInt("type_id"), rs.getString("type_name")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return accident;
        });
        Accident accident = idToAccident.get(accId);
        accident.addRule(Rule.of(rs.getInt("r_id"), rs.getString("r_name")));
        return accident;
    };

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Collection<Rule> getRules() {
        return jdbc.query("select id, name from rule",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    public Collection<AccidentType> getAccidentTypes() {
        return jdbc.query("select id, name from accident_type",
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }

    public Collection<Accident> getAccidents() {
        idToAccident.clear();
        jdbc.query(findAccidentsQuery, mapper);
        return idToAccident.values();
    }

    public Accident findAccidentById(int id) {
        String sql = findAccidentsQuery + "where a.id = ?";
        idToAccident.clear();
        jdbc.query(sql, mapper, id);
        return idToAccident.get(id);
    }

    public Accident saveOrUpdate(Accident accident, String[] rIds) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into accident (name, text, address, type_id) values (?, ?, ?, ?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);

        if (accident.getId() == 0) {
            accident.setId(keyHolder.getKey().intValue());
        }

        jdbc.update("delete from accident_rule where accident_id = ?", accident.getId());
        jdbc.batchUpdate("insert into accident_rule (accident_id, rule_id) values (?, ?)",
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, accident.getId());
                        ps.setInt(2, Integer.parseInt(rIds[i]));
                    }

                    public int getBatchSize() {
                        return rIds.length;
                    }
                }
        );
        return accident;
    }
}
