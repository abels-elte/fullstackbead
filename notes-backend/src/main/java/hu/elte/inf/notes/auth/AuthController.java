package hu.elte.inf.notes.auth;

import hu.elte.inf.notes.folder.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController()
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private JwtEncoder jwtEncoder;

    @PostMapping("/auth/sign-in")
    public ResponseEntity<UserInfo> signIn(@RequestBody User reqUser) {
        try {

            var authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqUser.getUsername(), reqUser.getPassword()));

            var user = (User)authentication.getPrincipal();

            var now = Instant.now();
            var expiry = 864000L; // 10 days

            var claims =
                    JwtClaimsSet.builder()
                            .issuer("http://inf.elte.hu")
                            .issuedAt(now)
                            .expiresAt(now.plusSeconds(expiry))
                            .subject(String.format("%s,%s", user.getId(), user.getUsername()))
                            .build();

            var token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .header(HttpHeaders.EXPIRES, now.plusSeconds(expiry).toString())
                    .body(user.toUserInfo());
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/auth/sign-up")
    public UserInfo signUp(@RequestBody User newUser) {
        var savedUser = userService.create(newUser);

        folderService.defaultFolder(savedUser);
        System.out.println(folderService.defaultFolder(savedUser));

        return new UserInfo(savedUser.getUsername(), savedUser.getDisplayName());
    }
}
