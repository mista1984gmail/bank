package com.mista1984.bank.domain.rates;

import java.util.HashMap;
import java.util.Map;

public class ExchangeRates {
    private Map<String,Double> map;

    public ExchangeRates() {
    }

    private ExchangeRates(Map<String, Double> map) {
        this.map = map;
    }
    public Map<String,Double> getExchangeRates(){
        Map<String,Double>map=new HashMap<>();
        map.put("BYN-BYN",1.0);
        map.put("BYN-RUB",29.39);
        map.put("BYN-USD",0.39);
        map.put("RUB-RUB",1.0);
        map.put("RUB-BYN",0.034);
        map.put("RUB-USD",0.013);
        map.put("USD-USD",1.0);
        map.put("USD-BYN",2.56);
        map.put("USD-RUB",75.19);
        return map;
    }
}
