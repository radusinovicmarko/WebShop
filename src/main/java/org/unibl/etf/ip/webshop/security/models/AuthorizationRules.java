package org.unibl.etf.ip.webshop.security.models;

import lombok.Data;

import java.util.List;

@Data
public class AuthorizationRules {
    private List<Rule> rules;
}
