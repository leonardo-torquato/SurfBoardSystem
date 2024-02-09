package com.b1system.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.b1system.models.createDtos.LoginDTO;
import com.b1system.models.createDtos.RegistrationDTO;
import com.b1system.utils.UnidadeFederacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.b1system.models.entities.User;
import com.b1system.models.dtos.LoginResponseDTO;
import com.b1system.models.entities.Role;
import com.b1system.repository.RoleRepository;
import com.b1system.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationService(final UserRepository userRepository,
                                 final RoleRepository roleRepository,
                                 final PasswordEncoder passwordEncoder,
                                 final AuthenticationManager authenticationManager,
                                 final TokenService tokenService){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public User registerUser(RegistrationDTO registrationDTO){

        String encodedPassword = passwordEncoder.encode(registrationDTO.getNotEncodedPassword());
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(User.builder()
                .username(registrationDTO.getUsername())
                .password(encodedPassword)
                .cpf(registrationDTO.getCpf())
                .email(registrationDTO.getEmail())
                .fullName(registrationDTO.getFullName())
                .nickname(registrationDTO.getNickname())
                .birthDate(LocalDate.parse(registrationDTO.getBirthDate()))
                .federacao(UnidadeFederacao.fromSigla(registrationDTO.getSiglaFederacao()))
                .authorities(authorities).build()
        );
    }

    public LoginResponseDTO loginUser(LoginDTO loginDTO){

        try{
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getNotEncodedPassword())
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(loginDTO.getUsername()).get(), token);

        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

}
