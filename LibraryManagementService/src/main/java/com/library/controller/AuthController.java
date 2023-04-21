package com.library.controller;

import com.library.dto.UserDto;
import com.library.entity.RefreshToken;
import com.library.entity.User;
import com.library.exception.TokenRefreshException;
import com.library.payload.request.TokenRefreshRequest;
import com.library.payload.response.TokenRefreshResponse;
import com.library.repository.UserRepository;
import com.library.security.jwt.JwtUtils;
import com.library.service.RefreshTokenService;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final RefreshTokenService refreshTokenService;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;


    @GetMapping("/register")
    public String registerUser(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                               Model model,
                               BindingResult result) {

        try{
            if (result.hasErrors()){
                model.addAttribute("userDto", userDto);
                result.toString();
                return "register";
            }

            if (userRepository.existsByUsername(userDto.getUsername())) {
                model.addAttribute("usernameError", "Username already exists!!!");
                return "register";
            }

            if (userRepository.existsByEmail(userDto.getEmail())) {
                model.addAttribute("emailError", "Email already exists!!!");
                return "register";
            }

            if (userDto.getPassword().equals(userDto.getRepeatPassword())){
                userDto.setPassword(encoder.encode(userDto.getPassword()));
                userService.save(userDto);
                model.addAttribute("userDto",userDto);
                model.addAttribute("success", "Registration successful!!!");
            }else{
                model.addAttribute("userDto", userDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
                return "register";
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        UserDetails userDetails = userService.loadUserByUsername(username);
        String jwt = jwtUtils.generateJwtToken(userDetails);

        model.addAttribute("token", jwt);
        return "redirect:/home";
    }


    @GetMapping("/client")
    public String clientPage(
            @RequestParam("token") String token,
             Model model) {
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUserNameFromJwtToken(token));
        model.addAttribute("user", userDetails.getUsername());
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(
            @RequestParam("token") String token,
            Model model) {
        UserDetails userDetails = userService.loadUserByUsername(jwtUtils.getUserNameFromJwtToken(token));
        model.addAttribute("user", userDetails.getUsername());
        model.addAttribute("title", "Dashboard");
        return "/admin/index";
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
//
//    @PostMapping("/signout")
//    public ResponseEntity<?> logoutUser() {
//        UserService userDetails = (UserService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long userId = userDetails.getId();
//        refreshTokenService.deleteByUserId(userId);
//        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
//    }
}

