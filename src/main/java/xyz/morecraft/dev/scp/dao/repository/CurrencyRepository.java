package xyz.morecraft.dev.scp.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import xyz.morecraft.dev.scp.dao.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, String>, QueryDslPredicateExecutor<Currency> {

}
