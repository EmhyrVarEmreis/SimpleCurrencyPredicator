package xyz.morecraft.dev.scp.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import xyz.morecraft.dev.scp.dao.User;

public interface UserRepository extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {

    User findOneByLogin(String login);

}
