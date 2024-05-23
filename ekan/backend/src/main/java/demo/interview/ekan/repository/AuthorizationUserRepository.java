package demo.interview.ekan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.interview.ekan.model.AuthorizationUser;
@Repository
public interface AuthorizationUserRepository  extends JpaRepository<AuthorizationUser, Long>  {
    AuthorizationUser findByLogin(String login); 
}

