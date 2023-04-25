package com.thunv.ecommerceou;

import com.thunv.ecommerceou.services.RenewalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EcommerceouApplicationTests {
	@Autowired
	private RenewalService renewalService;
	@Test
	void contextLoads() {
//		this.renewalService.initialTrialForNewAgency(3);
		this.renewalService.createOrderRenewal(3, 2);
		this.renewalService.scanAgencyRenewalInfo();
	}

}
