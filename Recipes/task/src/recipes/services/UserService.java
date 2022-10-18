package recipes.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.dao.UserDao;
import recipes.models.User;
import recipes.models.dto.UserDto;

@Service
@AllArgsConstructor
public class UserService {
    private final UserDao dao;
    private final PasswordEncoder passEnc;

    public boolean add(UserDto dto) {
        if (dao
                .findUserByUsername(dto.getEmail())
                .isPresent()) {
            return false;
        }
        dao.save(User
                .builder()
                .username(dto.getEmail())
                .password(
                        passEnc.encode(dto.getPassword()))
                .role("ROLE_USER")
                .build());
        return true;
    }
}