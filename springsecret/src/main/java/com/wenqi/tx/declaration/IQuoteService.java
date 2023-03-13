package com.wenqi.tx.declaration;

import cn.hutool.core.date.DateTime;

/**
 * @author liangwenqi
 * @date 2023/3/13
 */
public interface IQuoteService {
    Quote getQuote();

    Quote getQuoteByDateTime(DateTime dateTime);

    void saveQuote(Quote quote);

    void updateQuote(Quote quote);

    void deleteQuote(Quote quote);
}
