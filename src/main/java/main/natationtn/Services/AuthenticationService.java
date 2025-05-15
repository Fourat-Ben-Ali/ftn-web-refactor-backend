package main.natationtn.Services;

import lombok.RequiredArgsConstructor;
import main.natationtn.DTO.AuthenticationRequest;
import main.natationtn.DTO.AuthenticationResponse;
import main.natationtn.DTO.RegisterRequest;
import main.natationtn.Entities.User;
import main.natationtn.Repositories.UserRepository;
import main.natationtn.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register ( RegisterRequest request ) {
        User user = User.builder ( )
                .firstName ( request.getFirstname ( ) )
                .lastName ( request.getLastname ( ) )
                .email ( request.getEmail ( ) )
                .password ( passwordEncoder.encode ( request.getPassword ( ) ) )
                .phoneNumber ( request.getPhoneNumber ( ) )
                .role ( request.getUserRoles ( ) )
                .build ( );

        this.userRepository.save ( user );
        String jwtToken = jwtService.generateToken ( user );
        return AuthenticationResponse.builder ( ).token ( jwtToken ).build ( );


    }

    public AuthenticationResponse authenticate ( AuthenticationRequest request ) {
        authenticationManager.authenticate (
                new UsernamePasswordAuthenticationToken (
                        request.getEmail ( ) ,
                        request.getPassword ( )
                )

        );
        var user = userRepository.findByEmail ( request.getEmail ( ) ).orElseThrow ( );
        String jwtToken = jwtService.generateToken ( user );
        return AuthenticationResponse.builder ( ).token ( jwtToken ).firstName ( user.getFirstName ( ) ) .lastName ( user.getLastName ( ) ) . build ( );

    }
}
