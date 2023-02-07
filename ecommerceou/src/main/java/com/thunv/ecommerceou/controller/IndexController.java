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
    @PostMapping(path = "/test3")
    public ResponseEntity<ModelResponse> uploadCloudinary(){
        this.cartService.addToCart(this.userService.getUserByID(1),null,1);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse("200","","")
        );
    }
    @GetMapping(path = "/test")
    public ResponseEntity<ModelResponse> test(){
        String mailTo = "1951050080thu@ou.edu.vn";
        String subject = "Thank you for shopping at OU ecommerce";
        String title = String.format("Dear %s,", "thu van");
        String content = "We have received your order";
        String mailTemplate = "mail";
        String items  = "<tr style=\"border-bottom: 1px solid rgba(0,0,0,.05);\">\n"
                + "                            <td valign=\"middle\" width=\"80%\" style=\"text-align:left; padding: 0 2.5em;\">\n"
                + "                                <div class=\"product-entry\">\n"
                + String.format("<img src=\"%s\" alt=\"\" style=\"width: 100px; max-width: 600px; height: auto; margin-bottom: 20px; display: block;\">\n", "https://media3.scdn.vn/img2/2018/11_6/peaXU2_simg_de2fe0_500x500_maxb.png")
                + "                                    <div class=\"text\">\n"
                + String.format("<h4>%s</h4>\n", "Sách hay ho")
                + String.format("<span>%s</span>\n", "Siêu cấp")
                + String.format("<span>Qty:%d</span><br>\n", 4)
                + String.format("<span>Unit price: %,.0f VND</span>\n", 4000*1.0)
                + "                                    </div>\n"
                + "                                </div>\n"
                + "                            </td>\n"
                + "                            <td valign=\"middle\" width=\"20%\" style=\"text-align:left; padding: 0 2.5em;\">\n"
                + String.format("<span class=\"price\" style=\"color: #000; font-size: 20px;\">%,.0f VND</span>\n", 16000*1.0)
                + "                            </td>\n"
                + "                        </tr>";
        this.mailService.sendMail(mailTo,subject,title,content,items,mailTemplate);
        return ResponseEntity.status(HttpStatus.OK).body(
          new ModelResponse("200","get list categories success", mailTo)
        );
    }
}
