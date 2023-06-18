package hu.elte.inf.notes.auth;


import hu.elte.inf.notes.exceptions.SignUpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User create(User newUser) {
        if(userRepository.findByUsername(newUser.getUsername()).isPresent())
            throw new SignUpException("Username already exists!");

        if (newUser.getPassword() == null || newUser.getUsername() == null || newUser.getPassword().length() < 10 || newUser.getUsername().isBlank())
            throw new SignUpException("Error validating user details");

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }
}
