package com.wenqi.tx.declaration;

import cn.hutool.core.date.DateTime;

/**
 * @author liangwenqi
 * @date 2023/3/15
 */
public class QuoteServiceClient {
    private IQuoteService quoteService;

    public Quote getQuote() {
        return quoteService.getQuote();
    }

    public Quote getQuoteByDateTime(DateTime dateTime) {
        return quoteService.getQuoteByDateTime(dateTime);
    }

    public void saveQuote(Quote quote) {
        quoteService.saveQuote(quote);
    }

    public void updateQuote(Quote quote) {
        quoteService.updateQuote(quote);
    }

    public void deleteQuote(Quote quote) {
        quoteService.deleteQuote(quote);
    }

    public IQuoteService getQuoteService() {
        return quoteService;
    }

    public void setQuoteService(QuoteService quoteService) {
        this.quoteService = quoteService;
    }
}
