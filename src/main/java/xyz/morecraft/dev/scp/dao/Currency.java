package xyz.morecraft.dev.scp.dao;

import xyz.morecraft.dev.scp.dao.proto.DictionaryEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dct_currency")
public class Currency extends DictionaryEntity {

    public Currency() {
    }

    public Currency(String code, String name) {
        super(code, name);
    }

}
