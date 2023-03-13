package com.wenqi.tx.declaration;

import cn.hutool.core.date.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author liangwenqi
 * @date 2023/3/13
 */
public class QuoteService implements IQuoteService {
    private final JdbcTemplate jdbcTemplate;

    public QuoteService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Quote getQuote() {
        return  jdbcTemplate.queryForObject("SELECT * FROM user LIMIT 1", (rs, rowNum) -> new Quote());
    }

    @Override
    public Quote getQuoteByDateTime(DateTime dateTime) {
        throw new RuntimeException("not implemented method");
    }

    @Override
    public void saveQuote(Quote quote) {
        throw new RuntimeException("not implemented method");
    }

    @Override
    public void updateQuote(Quote quote) {
        throw new RuntimeException("not implemented method");
    }

    @Override
    public void deleteQuote(Quote quote) {
        throw new RuntimeException("not implemented method");
    }


}
