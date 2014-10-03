package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
