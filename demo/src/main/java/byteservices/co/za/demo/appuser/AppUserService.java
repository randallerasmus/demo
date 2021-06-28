package byteservices.co.za.demo.appuser;
/*
*  This class will deal with the security interface
*
*   This is how we will find users once we try to log in
* */


import byteservices.co.za.demo.registration.token.ConfirmationToken;
import byteservices.co.za.demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG,email)));
    }

    public String signUpUser(AppUser appUser) {
        // logic to deal with a password
        boolean userExist = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if(userExist) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        // save the user into db
        appUserRepository.save(appUser);

        // todo : send confirmation token
        // 1 -  create a token
        String token = UUID.randomUUID().toString();
        ConfirmationToken  confirmationToken = new ConfirmationToken(
                token,
                //createAt
                LocalDateTime.now(),
                //ExpiresAt
                LocalDateTime.now().plusMinutes(15),
                appUser
            );
        // 2 - save the token
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // todo: Send a email

        return token;
    }
}
