package org.zerhusen.security.repository;

import java.util.Collection;
import javax.transaction.Transactional;
import org.jboss.logging.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerhusen.model.security.User;

/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    @Query(value = "select * from user where id = ?1", nativeQuery = true)
    User UserxID(long id);
    
    @Query(value = "select * from user where username = ?1", nativeQuery = true)
    User UserByUsername(String mail);
    
    @Transactional
    @Modifying
    @Query(value = "update user set password = ?1 where username = ?2", nativeQuery = true)
    void UserEdit(String pass, String mail);
    
    @Transactional
    @Modifying
    @Query(value = "insert into user_authority (user_id, authority_id) values (?1, ?2)", nativeQuery = true)
    void newAuthority(long user_id, int authority_id);
    
}
