package byteservices.co.za.demo.registration;

import byteservices.co.za.demo.appuser.AppUser;
import byteservices.co.za.demo.appuser.AppUserRole;
import byteservices.co.za.demo.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {

       boolean isValidEmail = emailValidator
               .test(request.getEmail());

       if(!isValidEmail){
           throw new IllegalStateException("email not verified");
       }

       return appUserService.signUpUser(
               new AppUser(
                       request.getFirstName(),
                       request.getLastName(),
                       request.getEmail(),
                       request.getPassword(),
                       AppUserRole.USER
               )

       );
    }
}
