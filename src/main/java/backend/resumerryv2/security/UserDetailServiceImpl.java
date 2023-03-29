/* Licensed under InfoCat */
package backend.resumerryv2.security;

import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =
                userRepository
                        .findByEmailAndIsDeleteFalse(email)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("Could not found database"));

        log.info("User found in the database" + user);
        return new CustomUserDetails(email);
    }
}
