package com.edu.springbootapachecamal;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.stereotype.Component;

@Component
public class FileFilerPredicate implements Predicate {
    @Override
    public boolean matches(Exchange exchange) {
        return false;
    }

    @Override
    public void init(CamelContext context) {

    }
}
