package byteservices.co.za.demo.registration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;

import byteservices.co.za.demo.appuser.AppUser;
import byteservices.co.za.demo.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor

public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
