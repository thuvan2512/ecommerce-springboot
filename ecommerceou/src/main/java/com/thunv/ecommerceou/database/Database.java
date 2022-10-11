//package com.thunv.ecommerceou.database;
//
//import com.thunv.ecommerceou.models.pojo.*;
//import com.thunv.ecommerceou.repositories.*;
//import com.thunv.ecommerceou.services.RoleService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.Date;
//
//@Configuration
//public class Database {
//    @Autowired
//    private GenderRepository genderRepository;
//    @Autowired
//    private AuthProviderRepository authProviderRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private OrderStateRepository orderStateRepository;
//    @Autowired
//    private AgentFieldRepository agentFieldRepository;
//    @Autowired
//    private PaymentTypeRepository paymentTypeRepository;
//    @Autowired
//    private SellStatusRepository sellStatusRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//    @Bean
//    public void initDatabase() throws RuntimeException{
//        if (this.genderRepository.count() == 0){
//            this.genderRepository.save(new Gender(1,"MALE"));
//            this.genderRepository.save(new Gender(2,"FEMALE"));
//            this.genderRepository.save(new Gender(3,"UNDEFINED"));
//        }
//        if(this.authProviderRepository.count() == 0){
//            this.authProviderRepository.save(new AuthProvider(1,"DEFAULT"));
//            this.authProviderRepository.save(new AuthProvider(2,"GOOGLE"));
//            this.authProviderRepository.save(new AuthProvider(3,"FACEBOOK"));
//        }
//        if(this.roleRepository.count() == 0){
//            this.roleRepository.save(new Role(1,"ROLE_ADMIN"));
//            this.roleRepository.save(new Role(2,"ROLE_MANAGER"));
//            this.roleRepository.save(new Role(3,"ROLE_GENERAL"));
//        }
//        if (this.paymentTypeRepository.count() == 0){
//            this.paymentTypeRepository.save(new PaymentType(1,"BY CASH"));
//            this.paymentTypeRepository.save(new PaymentType(2,"BY E-WALLET"));
//        }
//        if (this.orderStateRepository.count() == 0){
//            this.orderStateRepository.save(new OrderState(1,"Wait for confirmation"));
//            this.orderStateRepository.save(new OrderState(2,"Accepted"));
//            this.orderStateRepository.save(new OrderState(3,"Packed"));
//            this.orderStateRepository.save(new OrderState(4,"Shipped"));
//            this.orderStateRepository.save(new OrderState(5,"Completed"));
//        }
//        if (this.sellStatusRepository.count() == 0){
//            this.sellStatusRepository.save(new SellStatus(1,"IN STOCK"));
//            this.sellStatusRepository.save(new SellStatus(2,"BEST SELLER"));
//            this.sellStatusRepository.save(new SellStatus(3,"PROMOTION"));
//            this.sellStatusRepository.save(new SellStatus(4,"SUPER SALE"));
//            this.sellStatusRepository.save(new SellStatus(5,"FREE SHIP"));
//            this.sellStatusRepository.save(new SellStatus(6,"TRENDING"));
//        }
//        if (this.agentFieldRepository.count() == 0){
//            this.agentFieldRepository.save(new AgentField(1,"Thời trang"));
//            this.agentFieldRepository.save(new AgentField(2,"Dược phẩm"));
//            this.agentFieldRepository.save(new AgentField(3,"Đồ gia dụng"));
//            this.agentFieldRepository.save(new AgentField(4,"Đồ handmade - Mỹ nghệ"));
//            this.agentFieldRepository.save(new AgentField(5,"Thực phẩm"));
//            this.agentFieldRepository.save(new AgentField(6,"Phụ kiện công nghệ"));
//            this.agentFieldRepository.save(new AgentField(7,"Đồ chơi trẻ em"));
//            this.agentFieldRepository.save(new AgentField(8,"Phụ kiện - Trang sức"));
//            this.agentFieldRepository.save(new AgentField(9,"Sách"));
//            this.agentFieldRepository.save(new AgentField(10,"Văn phòng phẩm"));
//            this.agentFieldRepository.save(new AgentField(11,"Chăm sóc thú cưng"));
//            this.agentFieldRepository.save(new AgentField(12,"Thể thao - Du lịch"));
//            this.agentFieldRepository.save(new AgentField(13,"Mỹ phẩm - Chăm sóc sắc đẹp"));
//            this.agentFieldRepository.save(new AgentField(14,"Dịch vụ"));
//            this.agentFieldRepository.save(new AgentField(15,"Xe - Phụ kiện xe"));
//            this.agentFieldRepository.save(new AgentField(16,"Khác"));
//
//        }
//        if (this.categoryRepository.count() == 0){
//            this.categoryRepository.save(new Category(1,"Moms, Kids & Babies","https://res.cloudinary.com/dec25/image/upload/v1658859831/099edde1ab31df35bc255912bab54a5e_tn_j8imem.png"));
//            this.categoryRepository.save(new Category(2,"Consumer Electronics","https://res.cloudinary.com/dec25/image/upload/v1658859831/978b9e4cb61c611aaaf58664fae133c5_tn_i1fwmx.png"));
//            this.categoryRepository.save(new Category(3,"Fashion","https://res.cloudinary.com/dec25/image/upload/v1658859831/687f3967b7c2fe6a134a2c11894eea4b_tn_hym300.png"));
//            this.categoryRepository.save(new Category(4,"Home & Living","https://res.cloudinary.com/dec25/image/upload/v1658859830/24b194a695ea59d384768b7b471d563f_tn_od4idc.png"));
//            this.categoryRepository.save(new Category(5,"Shoes","https://res.cloudinary.com/dec25/image/upload/v1658859831/74ca517e1fa74dc4d974e5d03c3139de_tn_ooekx6.png"));
//            this.categoryRepository.save(new Category(6,"Grocery","https://res.cloudinary.com/dec25/image/upload/v1658859831/c432168ee788f903f1ea024487f2c889_tn_smsulc.png"));
//            this.categoryRepository.save(new Category(7,"Computer & Accessories","https://res.cloudinary.com/dec25/image/upload/v1658859831/c3f3edfaa9f6dafc4825b77d8449999d_tn_x4pchf.png"));
//            this.categoryRepository.save(new Category(8,"Mobile & Gadgets","https://res.cloudinary.com/dec25/image/upload/v1658860482/31234a27876fb89cd522d7e3db1ba5ca_tn_v1p2uo.png"));
//            this.categoryRepository.save(new Category(9,"Sport & Outdoor","https://res.cloudinary.com/dec25/image/upload/v1658859830/6cb7e633f8b63757463b676bd19a50e4_tn_dardwd.png"));
//            this.categoryRepository.save(new Category(10,"Books & Stationery","https://res.cloudinary.com/dec25/image/upload/v1658860366/36013311815c55d303b0e6c62d6a8139_tn_vbcsc4.png"));
//            this.categoryRepository.save(new Category(11,"Home Appliances","https://res.cloudinary.com/dec25/image/upload/v1658859830/7abfbfee3c4844652b4a8245e473d857_tn_adkwtb.png"));
//            this.categoryRepository.save(new Category(12,"Cameras","https://res.cloudinary.com/dec25/image/upload/v1658859832/ec14dd4fc238e676e43be2a911414d4d_tn_iw5jo7.png"));
//            this.categoryRepository.save(new Category(13,"Watches","https://res.cloudinary.com/dec25/image/upload/v1658859831/86c294aae72ca1db5f541790f7796260_tn_axqtnt.png"));
//            this.categoryRepository.save(new Category(14,"Automotive","https://res.cloudinary.com/dec25/image/upload/v1658859831/3fb459e3449905545701b418e8220334_tn_qa92jt.png"));
//            this.categoryRepository.save(new Category(15,"Home care","https://res.cloudinary.com/dec25/image/upload/v1658860366/cd8e0d2e6c14c4904058ae20821d0763_tn_ks3olq.png"));
//            this.categoryRepository.save(new Category(16,"Toys","https://res.cloudinary.com/dec25/image/upload/v1658860366/ce8f8abc726cafff671d0e5311caa684_tn_qbzpev.png"));
//
//        }
//        if (this.userRepository.count() == 0){
//            User userAdmin = new User(1,"https://res.cloudinary.com/dec25/image/upload/v1655434671/u4uid1cmt7sn7gbxoeu7.jpg",
//                    "thunv.admin","Thu","Nguyen Van","1951050080thu@ou.edu.vn","0784301745","116/36 Duong Quang Ham, p5 Go Vap,tp Ho Chi Minh");
//            userAdmin.setGender(this.genderRepository.findById(1).orElseThrow(() -> new RuntimeException("Create admin account failed")));
//            userAdmin.setAuthProvider(this.authProviderRepository.findById(1).orElseThrow(() -> new RuntimeException("Create admin account failed")));
//            userAdmin.setRole(this.roleRepository.findById(1).orElseThrow(() -> new RuntimeException("Create admin account failed")));
//            userAdmin.setJoinedDate(new Date());
//            userAdmin.setIsActive(1);
//            userAdmin.setPassword(this.passwordEncoder.encode("123456"));
//            this.userRepository.save(userAdmin);
//        }
//    }
////    private static final Logger logger = LoggerFactory.getLogger(Database.class);
////    @Bean
////    CommandLineRunner initDatabase(ProductRepository productRepository){
////        return new CommandLineRunner() {
////            @Override
////            public void run(String... args) throws Exception {
//////                Product p1 = new Product(1,"Iphone 10",10);
//////                Product p2 = new Product(2,"Ipad 1",20);
//////                Product p3 = new Product(3,"Iphone 14",100);
//////                Product p4 = new Product(4,"Ipad 2",200);
//////                logger.info(productRepository.save(p1).toString());
//////                logger.info(productRepository.save(p2).toString());
//////                logger.info(productRepository.save(p3).toString());
//////                logger.info(productRepository.save(p4).toString());
////            }
////        };
////    }
//}