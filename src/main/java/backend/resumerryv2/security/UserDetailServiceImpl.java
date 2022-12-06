/* Licensed under InfoCat */
package backend.resumerryv2.security;

import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);
    if (user == null) {
      log.error("User not found in the database {}", email);
      throw new UsernameNotFoundException("User not found in the database");
    } else {
      log.info("User found in the database: {}", email);
    }

    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    return new CustomUserDetails(email);
  }

  public User saveUser(User user) {
    log.info("Saving new user {} to the db", user.getNickname());
    return userRepository.save(user);
  }

  public Optional<User> getUser(String email) {
    return userRepository.findByEmail(email);
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }
}
