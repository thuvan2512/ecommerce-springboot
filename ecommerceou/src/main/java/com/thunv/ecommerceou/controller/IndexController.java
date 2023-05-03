package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.repositories.AgencyRepository;
import com.thunv.ecommerceou.repositories.AgentFieldRepository;
import com.thunv.ecommerceou.services.CartService;
import com.thunv.ecommerceou.services.CloudinaryService;
import com.thunv.ecommerceou.services.MailService;
import com.thunv.ecommerceou.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class IndexController {
    @Autowired
    private AgentFieldRepository a;
    @Autowired
    private Environment env;
    @Autowired
    private MailService mailService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

}
