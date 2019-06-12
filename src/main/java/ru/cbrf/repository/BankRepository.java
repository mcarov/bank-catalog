package ru.cbrf.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.cbrf.domain.Bank;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BankRepository {
    private final NamedParameterJdbcTemplate template;

    private final RowMapper<Bank> fullRowMapper = (resultSet, i) -> {
        Bank bank = new Bank();

        bank.setReal(resultSet.getString(1));
        bank.setPzn(resultSet.getString(2));
        bank.setUer(resultSet.getString(3));
        bank.setRgn(resultSet.getString(4));
        bank.setInd(resultSet.getString(5));
        bank.setTnp(resultSet.getString(6));
        bank.setNnp(resultSet.getString(7));
        bank.setAdr(resultSet.getString(8));
        bank.setRkc(resultSet.getString(9));
        bank.setNameP(resultSet.getString(10));
        bank.setNewNum(resultSet.getString(11));
        bank.setPhoneNum(resultSet.getString(12));
        bank.setRegNum(resultSet.getString(13));
        bank.setOkpo(resultSet.getString(14));
        bank.setChangingDate(Date.from(Instant.ofEpochSecond(resultSet.getLong(15))));
        bank.setKsnp(resultSet.getString(16));
        bank.setInclusionDate(Date.from(Instant.ofEpochSecond(resultSet.getLong(17))));
        Object controlDate = resultSet.getObject(18);
        if(controlDate != null)
            bank.setControlDate(Date.from(Instant.ofEpochSecond((Integer) controlDate)));

        return bank;
    };

    private final RowMapper<Bank> simpleRowMapper = (resultSet, i) -> {
        Bank bank = new Bank();
        bank.setNameP(resultSet.getString(1));
        bank.setNewNum(resultSet.getString(2));

        return bank;
    };

    public BankRepository(DataSource source) {
        this.template = new NamedParameterJdbcTemplate(source);
    }

    @PostConstruct
    public void init() {
        template.getJdbcTemplate().execute("CREATE TABLE IF NOT EXISTS banks (" +
                "real TEXT, " +
                "pzn TEXT, " +
                "uer TEXT NOT NULL, " +
                "rgn TEXT NOT NULL, " +
                "ind TEXT, " +
                "tnp TEXT, " +
                "nnp TEXT, " +
                "adr TEXT, " +
                "rkc TEXT, " +
                "name_p TEXT NOT NULL, " +
                "new_num TEXT NOT NULL PRIMARY KEY, " +
                "phone_num TEXT, " +
                "reg_num TEXT, " +
                "okpo TEXT, " +
                "changing_date INTEGER NOT NULL, " +
                "ksnp TEXT, " +
                "inclusion_date INTEGER NOT NULL, " +
                "control_date);");
    }

    public int size() {
        Optional<Integer> size = Optional.ofNullable(template.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM banks", Integer.class));
        return size.orElse(0);
    }

    public List<Bank> searchBanksByBik(String query) {
        return template.query("SELECT name_p, new_num FROM banks WHERE new_num LIKE :query;",
                Map.of("query", String.join("", "%", query.toLowerCase(), "%")),
                simpleRowMapper);
    }

    public List<Bank> getBanksByPzn(String pzn) {
        return template.query("SELECT name_p, new_num FROM banks WHERE pzn LIKE :pzn;",
                Map.of("pzn", pzn),
                simpleRowMapper);
    }

    public List<Bank> getBanksByRgn(String rgn) {
        return template.query("SELECT name_p, new_num FROM banks WHERE rgn LIKE :rgn;",
                Map.of("rgn", rgn),
                simpleRowMapper);
    }

    public List<Bank> getBanks(int offset, int limit) {
        return template.query("SELECT name_p, new_num FROM banks LIMIT :offset, :limit;",
                Map.of("offset", offset, "limit", limit), simpleRowMapper);
    }

    public Bank getBankByBik(String bik) {
        return template.queryForObject("SELECT real, pzn, uer, rgn, ind, tnp, nnp, adr, rkc, " +
                "name_p, new_num, phone_num, reg_num, okpo, changing_date, ksnp, inclusion_date, control_date " +
                "FROM banks WHERE new_num LIKE :bik;", Map.of("bik", bik), fullRowMapper);
    }

    public void saveBank(Bank bank) {
        if(StringUtils.isEmpty(bank.getNewNum())) {
            template.update("INSERT INTO banks (" +
                    "real, " +
                    "pzn, " +
                    "uer, " +
                    "rgn, " +
                    "ind, " +
                    "tnp, " +
                    "nnp, " +
                    "adr, " +
                    "rkc, " +
                    "name_p, " +
                    "new_num, " +
                    "phone_num, " +
                    "reg_num, " +
                    "okpo, " +
                    "changing_date, " +
                    "ksnp, " +
                    "inclusion_date, " +
                    "control_date) " +
                    "VALUES (:real, :pzn, :uer, :rgn, :ind, :tnp, :nnp, :adr, :rkc, " +
                    ":nameP, :newNum, :phoneNum, :regNum, :okpo, :changingDate, :ksnp, :inclusionDate, :controlDate);",
                    getParamMap(bank));

            return;
        }

        template.update("INSERT INTO banks (" +
                    "real, " +
                    "pzn, " +
                    "uer, " +
                    "rgn, " +
                    "ind, " +
                    "tnp, " +
                    "nnp, " +
                    "adr, " +
                    "rkc, " +
                    "name_p, " +
                    "new_num, " +
                    "phone_num, " +
                    "reg_num, " +
                    "okpo, " +
                    "changing_date, " +
                    "ksnp, " +
                    "inclusion_date, " +
                    "control_date) " +
                    "VALUES (:real, :pzn, :uer, :rgn, :ind, :tnp, :nnp, :adr, :rkc, " +
                    ":nameP, :newNum, :phoneNum, :regNum, :okpo, :changingDate, :ksnp, :inclusionDate, :controlDate) " +
                    "ON CONFLICT(new_num) DO UPDATE SET " +
                    "real = :real, pzn = :pzn, uer = :uer, rgn = :rgn, ind = :ind, tnp = :tnp, " +
                    "adr = :adr, rkc = :rkc, name_p = :nameP, new_num = :newNum, " +
                    "phone_num = :phoneNum, reg_num = :regNum, okpo = :okpo, changing_date = :changingDate, " +
                    "ksnp = :ksnp, inclusion_date = :inclusionDate, control_date = :controlDate;",
        getParamMap(bank));
    }

    public void removeBank(String bik) {
        template.update("DELETE FROM banks WHERE new_num LIKE :bik", Map.of("bik", bik));
    }

    private SqlParameterSource getParamMap(Bank bank) {
        return new MapSqlParameterSource().
                addValue("real", bank.getReal()).
                addValue("pzn", bank.getPzn()).
                addValue("uer", bank.getUer()).
                addValue("rgn", bank.getRgn()).
                addValue("ind", bank.getInd()).
                addValue("tnp", bank.getTnp()).
                addValue("nnp", bank.getNnp()).
                addValue("adr", bank.getAdr()).
                addValue("rkc", bank.getRkc()).
                addValue("nameP", bank.getNameP()).
                addValue("newNum", bank.getNewNum()).
                addValue("phoneNum", bank.getPhoneNum()).
                addValue("regNum", bank.getRegNum()).
                addValue("okpo", bank.getOkpo()).
                addValue("changingDate", bank.getChangingDate().getTime()/1000).
                addValue("ksnp", bank.getKsnp()).
                addValue("inclusionDate", bank.getInclusionDate().getTime()/1000).
                addValue("controlDate",
                        bank.getControlDate() != null ? bank.getControlDate().getTime()/1000 : null);
    }
}
