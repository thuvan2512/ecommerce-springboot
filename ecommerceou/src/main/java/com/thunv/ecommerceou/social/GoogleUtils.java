package com.thunv.ecommerceou.social;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thunv.ecommerceou.models.GoogleEntity;
import com.thunv.ecommerceou.models.enumerate.NotificationImages;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.UserRepository;
import com.thunv.ecommerceou.services.AuthProviderService;
import com.thunv.ecommerceou.services.NotifyService;
import com.thunv.ecommerceou.services.RoleService;
import com.thunv.ecommerceou.services.UserService;
import org.apache.http.client.ClientProtocolException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.io.IOException;

@Component
public class GoogleUtils {
    @Autowired
    private Environment env;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private AuthProviderService authProviderService;
    private static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    private static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    private static final String GOOGLE_GRANT_TYPE = "authorization_code";

    public String getToken(final String code) throws ClientProtocolException, IOException {
        String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", env.getProperty("google.clientID").toString())
                        .add("client_secret", env.getProperty("google.clientSecret").toString())
                        .add("redirect_uri", env.getProperty("google.redirectURL").toString()).add("code", code)
                        .add("grant_type", GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }

    public GoogleEntity getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        System.err.println(response);
        ObjectMapper mapper = new ObjectMapper();
        GoogleEntity googleInfo = mapper.readValue(response, GoogleEntity.class);
        System.out.println(googleInfo);
        return googleInfo;
    }

    public boolean createUser(GoogleEntity googleEntity) {
        String username = googleEntity.getEmail().split("@")[0];
        Random rand = new Random();
        while (this.userService.checkExistUsername(username)) {
            username = username + rand.nextInt(100);
        }
        if (!this.userService.checkExistEmail(googleEntity.getEmail())) {
            User user = new User();
            user.setAvatar(googleEntity.getPicture());
            user.setUsername(username);
            user.setAuthProvider(this.authProviderService.getAuthProviderByID(2));
            user.setPassword(this.passwordEncoder.encode(env.getProperty("auth.secretKey")));
            user.setRole(this.roleService.getRoleByID(3));
            user.setJoinedDate(new Date());
            user.setEmail(googleEntity.getEmail());
            try {
                this.userRepository.save(user);
                String recipientAgency = String.format("user-%s", user.getId());
                String title = "Welcome to Open Market";
                String detail = "Welcome to the Open Market. Wish you have a great shopping experience here !!!";
                String type = "Welcome new user";
                this.notifyService.pushNotify(recipientAgency, NotificationImages.APP_LOGO.getValue(), title, detail, type);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public UserDetails buildUser(User u) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(u.getRole().getName()));
        UserDetails userDetail = new org.springframework.security.core.userdetails.User(u.getUsername(),env.getProperty("auth.secretKey").toString(), authorities);
        return userDetail;
    }
}
