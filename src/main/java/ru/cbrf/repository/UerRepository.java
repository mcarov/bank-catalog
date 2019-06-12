package ru.cbrf.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.cbrf.domain.Uer;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UerRepository {
    private final NamedParameterJdbcTemplate template;

    public UerRepository(DataSource source) {
        this.template = new NamedParameterJdbcTemplate(source);
    }

    @PostConstruct
    public void init() {
        template.getJdbcTemplate().execute("CREATE TABLE IF NOT EXISTS uer_data (" +
                "v_key INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "uer TEXT, " +
                "name TEXT);");
    }

    public List<Uer> getAllUer() {
        return template.query("SELECT v_key, uer, name FROM uer_data;",
                new BeanPropertyRowMapper<>(Uer.class));
    }

    public String getUerNameByCode(String code) {
        List<String> uerList = template.query("SELECT name FROM uer_data WHERE uer LIKE :uer;", Map.of("uer", code),
                (resultSet, i) -> resultSet.getString(1));

        return uerList.isEmpty() ? "" :uerList.get(0);
    }

    public void saveUer(Uer uer) {
        if(uer.getVKey() == 0) {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            template.update("INSERT INTO uer_data (uer, name) " +
                    "VALUES (:uer, :name);",
                    getParamMap(uer), keyHolder);

            long vKey = Objects.requireNonNull(keyHolder.getKey()).longValue();
            uer.setVKey(vKey);

            return;
        }

        template.update("INSERT INTO uer_data (v_key, uer, name) " +
                "VALUES (:vKey, :uer, :name) " +
                "ON CONFLICT(v_key) DO UPDATE SET " +
                "uer = :uer, name = :name;",
                getParamMap(uer));
    }

    private SqlParameterSource getParamMap(Uer uer) {
        return new MapSqlParameterSource().
                addValue("vKey", uer.getVKey()).
                addValue("uer", uer.getUer()).
                addValue("name", uer.getName());
    }
}
