package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.JwtDTO;
import com.thunv.ecommerceou.models.GoogleEntity;
import com.thunv.ecommerceou.res.JwtResponse;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.UserService;
import com.thunv.ecommerceou.jwt.JwtTokenUtils;
import com.thunv.ecommerceou.social.FacebookUtils;
import com.thunv.ecommerceou.social.GoogleUtils;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Date;

@RestController
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private FacebookUtils facebookUtils;
    @Autowired
    private Utils utils;
    @Autowired
    private GoogleUtils googleUtils;
    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<ModelResponse> createAuthenticationToken(@RequestBody JwtDTO authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtils.generateToken(userDetails);
        final Date expirationDate = this.jwtTokenUtils.getExpirationDateFromToken(token);
        String ex = this.utils.getDateFormatter().format(expirationDate);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse("200","Get auth token successfully", new JwtResponse(token,ex))
        );
    }
//    @GetMapping("/login-google/get-code")
//    public ResponseEntity<ModelResponse> loginGoogle(HttpServletRequest request) throws IOException {
//        String code = request.getParameter("code");
//        if (code == null || code.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ModelResponse("200","Get code failed","" )
//            );
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ModelResponse("200","Get auth token failed",code )
//        );
//    }
    @GetMapping("/login-google")
    public ResponseEntity<ModelResponse> loginGoogle(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
        System.out.println(code);
//        String ms = "login google successfully";
//        String codeRes = "200";

        if (code == null || code.isEmpty()) {
            String msg = "Get%20auth%20token%20failed";
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(String.format("%s?msg=%s",this.env.getProperty("fe.url"), msg))).build();
        }
        String accessToken = googleUtils.getToken(code);
        GoogleEntity googleEntity = googleUtils.getUserInfo(accessToken);
        if (this.userService.checkExistEmail(googleEntity.getEmail()) != true) {
            this.googleUtils.createUser(googleEntity);
            UserDetails userDetail = googleUtils.buildUser(this.userService.loadUserByEmail(googleEntity.getEmail()).get(0));
            final String token = jwtTokenUtils.generateToken(userDetail);
//            final Date expirationDate = this.jwtTokenUtils.getExpirationDateFromToken(token);
//            String ex = this.utils.getDateFormatter().format(expirationDate);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(String.format("%s?jwt=%s",this.env.getProperty("fe.url"), token))).build();
        } else {
            int auth = this.userService.loadUserByEmail(googleEntity.getEmail()).get(0).getAuthProvider().getId();
            if (auth == 2) {
                UserDetails userDetail = googleUtils.buildUser(this.userService.loadUserByEmail(googleEntity.getEmail()).get(0));
                final String token = jwtTokenUtils.generateToken(userDetail);
//                final Date expirationDate = this.jwtTokenUtils.getExpirationDateFromToken(token);
//                String ex = this.utils.getDateFormatter().format(expirationDate);
                return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(String.format("%s?jwt=%s",this.env.getProperty("fe.url"), token))).build();
//                return ResponseEntity.status(HttpStatus.OK).body(
//                        new ModelResponse("200","Get auth token successfully", new JwtResponse(token,ex))
//                );
            } else {
                String msg = "Email%20already%20exist%20in%20this%20system";
                return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(String.format("%s?msg=%s",this.env.getProperty("fe.url"), msg))).build();
            }
        }
    }
    @GetMapping("/login-facebook")
    public ResponseEntity<ModelResponse> loginFacebook(HttpServletRequest request) {
        String code = request.getParameter("code");
        String accessToken = "";
        try {
            accessToken = facebookUtils.getToken(code);
        } catch (Exception e) {
            String msg = "Get%20auth%20token%20failed";
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(String.format("%s?msg=%s",this.env.getProperty("fe.url"), msg))).build();
        }
        com.restfb.types.User facebookInfo = facebookUtils.getUserInfo(accessToken);
        if (this.userService.checkExistEmail(facebookInfo.getEmail()) != true) {
            this.facebookUtils.createUser(facebookInfo);
            UserDetails userDetail = this.facebookUtils.buildUser(this.userService.loadUserByEmail(facebookInfo.getEmail()).get(0));
            final String token = jwtTokenUtils.generateToken(userDetail);
//            final Date expirationDate = this.jwtTokenUtils.getExpirationDateFromToken(token);
//            String ex = this.utils.getDateFormatter().format(expirationDate);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(String.format("%s?jwt=%s",this.env.getProperty("fe.url"), token))).build();
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ModelResponse("200","Get auth token successfully", new JwtResponse(token,ex))
//            );
        } else {
            int auth = this.userService.loadUserByEmail(facebookInfo.getEmail()).get(0).getAuthProvider().getId();
            if (auth == 3) {
                UserDetails userDetail = this.facebookUtils.buildUser(this.userService.loadUserByEmail(facebookInfo.getEmail()).get(0));
                final String token = jwtTokenUtils.generateToken(userDetail);
                return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(String.format("%s?jwt=%s",this.env.getProperty("fe.url"), token))).build();
            } else {
                String msg = "Email%20already%20exist%20in%20this%20system";
                return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(String.format("%s?msg=%s",this.env.getProperty("fe.url"), msg))).build();
            }
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
