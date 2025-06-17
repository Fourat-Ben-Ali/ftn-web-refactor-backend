package main.natationtn.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse  {

    private  String token;
    private  String firstName;
    private  String lastName;
}
