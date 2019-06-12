package ru.cbrf.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.cbrf.domain.Pzn;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class PznRepository {
    private final NamedParameterJdbcTemplate template;

    public PznRepository(DataSource source) {
        this.template = new NamedParameterJdbcTemplate(source);
    }

    @PostConstruct
    public void init() {
        template.getJdbcTemplate().execute("CREATE TABLE IF NOT EXISTS pzn_data (" +
                "v_key INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pzn TEXT, " +
                "imy TEXT, " +
                "name TEXT);");
    }

    public List<Pzn> getAllPzn() {
        return template.query("SELECT v_key, pzn, imy, name FROM pzn_data;",
                new BeanPropertyRowMapper<>(Pzn.class));
    }

    public String getPznNameByCode(String code) {
        List<String> pznList = template.query("SELECT name FROM pzn_data WHERE pzn LIKE :pzn;",
                Map.of("pzn", code), (resultSet, i) -> resultSet.getString(1));

        return pznList.isEmpty() ? "" : pznList.get(0);
    }

    public void savePzn(Pzn pzn) {
        if(pzn.getVKey() == 0) {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            template.update("INSERT INTO pzn_data (pzn, imy, name) " +
                    "VALUES (:pzn, :imy, :name);",
                    getParamMap(pzn), keyHolder);

            long vKey = Objects.requireNonNull(keyHolder.getKey()).longValue();
            pzn.setVKey(vKey);

            return;
        }

        template.update("INSERT INTO pzn_data (v_key, pzn, imy, name) " +
                "VALUES (:vKey, :pzn, :imy, :name) " +
                "ON CONFLICT(v_key) DO UPDATE SET " +
                "pzn = :pzn, imy = :imy, name = :name;",
                getParamMap(pzn));
    }

    private SqlParameterSource getParamMap(Pzn pzn) {
        return new MapSqlParameterSource().
                addValue("vKey", pzn.getVKey()).
                addValue("pzn", pzn.getPzn()).
                addValue("imy", pzn.getImy()).
                addValue("name", pzn.getName());
    }
}
