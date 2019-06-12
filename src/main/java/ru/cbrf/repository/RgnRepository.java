package ru.cbrf.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.cbrf.domain.Rgn;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class RgnRepository {
    private final NamedParameterJdbcTemplate template;

    public RgnRepository(DataSource source) {
        this.template = new NamedParameterJdbcTemplate(source);
    }

    @PostConstruct
    public void init() {
        template.getJdbcTemplate().execute("CREATE TABLE IF NOT EXISTS rgn_data (" +
                "v_key INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "rgn TEXT, " +
                "name TEXT, " +
                "center TEXT, " +
                "name_t TEXT);");
    }

    public List<Rgn> getAllRgn() {
        return template.query("SELECT v_key, rgn, name, center, name_t FROM rgn_data;",
                new BeanPropertyRowMapper<>(Rgn.class));
    }

    public String getRgnNameByCode(String code) {
        List<String> rgnList = template.query("SELECT name FROM rgn_data WHERE rgn LIKE :rgn;",
                Map.of("rgn", code), (resultSet, i) -> resultSet.getString(1));

        return rgnList.isEmpty() ? "" : rgnList.get(0);
    }

    public void saveRgn(Rgn rgn) {
        if(rgn.getVKey() == 0) {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            template.update("INSERT INTO rgn_data (rgn, name, center, name_t) " +
                    "VALUES (:rgn, :name, :center, :nameT);",
                    getParamMap(rgn), keyHolder);

            long vKey = Objects.requireNonNull(keyHolder.getKey()).longValue();
            rgn.setVKey(vKey);

            return;
        }

        template.update("INSERT INTO rgn_data (v_key, rgn, name, center, name_t) " +
                "VALUES (:vKey, :rgn, :name, :center, :nameT) " +
                "ON CONFLICT(v_key) DO UPDATE SET " +
                "rgn = :rgn, name = :name, center = :center, name_t = :nameT", getParamMap(rgn));
    }

    private SqlParameterSource getParamMap(Rgn rgn) {
        return new MapSqlParameterSource().
                addValue("vKey", rgn.getVKey()).
                addValue("rgn", rgn.getRgn()).
                addValue("name", rgn.getName()).
                addValue("center", rgn.getCenter()).
                addValue("nameT", rgn.getNameT());
    }
}
