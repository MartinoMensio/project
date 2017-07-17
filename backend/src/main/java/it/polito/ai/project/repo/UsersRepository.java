package it.polito.ai.project.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.polito.ai.project.repo.entities.User;

public interface UsersRepository extends PagingAndSortingRepository<User, Long> {
	
	@Modifying
	@Query(value = "INSERT INTO users(nickname, email, password) "
			+ "VALUES (:nickname, :mail, crypt(:password, gen_salt('bf')))", nativeQuery = true)
	public int saveUserWithEncPwd(@Param("nickname") String nickname, @Param("mail") String mail, @Param("password") String password); 
	
	
	User findByEmail(String email);
	
	@Modifying
	@Query(value = "UPDATE users "
			+ "SET password = crypt(:pwd, gen_salt('bf')) "
			+ "WHERE email = :email", nativeQuery = true)
	public int changeUserPassword(@Param("email") String email, @Param("pwd") String newPassword);

}
