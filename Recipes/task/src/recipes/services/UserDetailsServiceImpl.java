package recipes.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.dao.UserDao;
import recipes.models.UserDetailsImpl;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String username) throws
                                           UsernameNotFoundException {

        if (dao
                .findUserByUsername(username)
                .isPresent()) {
            return new UserDetailsImpl(dao
                            .findUserByUsername(username)
                            .get());
        }
        throw new UsernameNotFoundException(
                "Not found: " + username);
    }
}
