package ru.cbrf.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.cbrf.domain.Tnp;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class TnpRepository {
    private final NamedParameterJdbcTemplate template;

    public TnpRepository(DataSource source) {
        this.template = new NamedParameterJdbcTemplate(source);
    }

    @PostConstruct
    public void init() {
        template.getJdbcTemplate().execute("CREATE TABLE IF NOT EXISTS tnp_data (" +
                "v_key INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tnp TEXT, " +
                "full_name TEXT, " +
                "short_name TEXT);");
    }

    public List<Tnp> getAllTnp() {
        return template.query("SELECT v_key, tnp, full_name, short_name FROM tnp_data;",
                new BeanPropertyRowMapper<>(Tnp.class));
    }

    public String getTnpNameByCode(String code) {
        List<String> tnpList = template.query("SELECT full_name FROM tnp_data WHERE tnp LIKE :tnp;",
                Map.of("tnp", code), (resultSet, i) -> resultSet.getString(1));

        return tnpList.isEmpty() ? "" : tnpList.get(0);
    }

    public void saveTnp(Tnp tnp) {
        if(tnp.getVKey() == 0) {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            template.update("INSERT INTO tnp_data (tnp, full_name, short_name) " +
                    "VALUES (:tnp, :fullName, :shortName);",
                    getParamMap(tnp), keyHolder);

            long vKey = Objects.requireNonNull(keyHolder.getKey()).longValue();
            tnp.setVKey(vKey);

            return;
        }

        template.update("INSERT INTO tnp_data (v_key, tnp, full_name, short_name) " +
                "VALUES (:vKey, :tnp, :fullName, :shortName) " +
                "ON CONFLICT (v_key) DO UPDATE SET " +
                "tnp = :tnp, full_name = :fullName, short_name = :shortName;",
                getParamMap(tnp));
    }

    private SqlParameterSource getParamMap(Tnp tnp) {
        return new MapSqlParameterSource().
                addValue("vKey", tnp.getVKey()).
                addValue("tnp", tnp.getTnp()).
                addValue("fullName", tnp.getFullName()).
                addValue("shortName", tnp.getShortName());
    }
}
