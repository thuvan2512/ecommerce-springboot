package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.repositories.CartItemRepository;
import com.thunv.ecommerceou.repositories.CartRepository;
import com.thunv.ecommerceou.repositories.PromotionCodeRepository;
import com.thunv.ecommerceou.repositories.PromotionProgramRepository;
import com.thunv.ecommerceou.services.FollowService;
import com.thunv.ecommerceou.services.NotifyService;
import com.thunv.ecommerceou.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionProgramRepository promotionProgramRepository;
    @Autowired
    private PromotionCodeRepository promotionCodeRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private FollowService followService;
    @Autowired
    private NotifyService notifyService;

    @Override
    public PromotionProgram getPromotionProgramByID(Integer id) throws RuntimeException{
        return this.promotionProgramRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can not find program with id = " + id));
    }

    @Override
    public PromotionCode getPromotionCodeByID(String id) throws RuntimeException{
        return this.promotionCodeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can not find code with id = " + id));
    }

    @Override
    public List<PromotionCode> getListPublishPromotionCodeByProgramID(PromotionProgram promotionProgram) throws RuntimeException{
        return this.promotionCodeRepository.getListPromotionCodeByProgram(promotionProgram);
    }

    @Override
    public List<PromotionCode> getAllPromotionCodeByProgramID(PromotionProgram promotionProgram) throws RuntimeException{
        return this.promotionCodeRepository.getListPromotionCodeByProgramForManage(promotionProgram);
    }

    @Override
    public List<PromotionProgram> getListProgramByListAgencyID(List<Integer> listAgencyID, Integer top) throws RuntimeException{
        return this.promotionProgramRepository.getListProgramByListAgencyID(listAgencyID, top);
    }

    @Override
    public List<PromotionProgram> getALLProgramByAgency(Agency agency) throws RuntimeException{
        return this.promotionProgramRepository.getAllProgramByListAgency(agency);
    }

    @Override
    public List<PromotionProgram> getAllPublishProgram() {
        return this.promotionProgramRepository.getAllPublishProgram();
    }

    @Override
    public PromotionProgram createPromotionProgram(PromotionProgram promotionProgram) throws RuntimeException{
        List<FollowAgency> followAgencyList = this.followService.getListFollowByAgency(promotionProgram.getAgency());
        this.notifyService.pushListFollowNotifyPromotionForUser(followAgencyList, promotionProgram);
        return this.promotionProgramRepository.save(promotionProgram);
    }

    @Override
    public boolean checkExistCode(String code) throws RuntimeException{
        List<PromotionCode> promotionCodeList = this.promotionCodeRepository.findByCode(code);
        if (promotionCodeList.size() > 0){
            return true;
        }
        return false;
    }

    @Override
    public PromotionCode createPromotionCode(PromotionCode promotionCode) throws RuntimeException{
        return this.promotionCodeRepository.save(promotionCode);
    }

    @Override
    public Object previewDiscountByVoucher(User user, PromotionCode promotionCode) throws RuntimeException{
        Map<Object, Object> result = new HashMap<>();
        Cart cart;
        if (this.cartRepository.existsByAuthor(user)){
            cart = this.cartRepository.findByAuthor(user).get(0);
        }else {
            cart = new Cart();
            cart.setAuthor(user);
            this.cartRepository.save(cart);
        }
        List<CartItem> cartItemList = this.cartItemRepository.findByCart(cart).stream().toList();
        Map<Integer, List<CartItem>> groupItemByAgency =
                cartItemList.stream().collect(Collectors.groupingBy(item -> item.getItemPost().getSalePost().getAgency().getId()));
        Integer agencyID = promotionCode.getProgram().getAgency().getId();
        List<CartItem> cartItemListByAgency = groupItemByAgency.get(agencyID);
        if (cartItemListByAgency == null){
            throw new RuntimeException("list cart items not found !!!");
        }
        Double totalPrice = 0.0;
        Double discount = 0.0;
        PromotionProgram promotionProgram = promotionCode.getProgram();
        for (CartItem cartItem: cartItemListByAgency){
            Double total = cartItem.getQuantity() * cartItem.getItemPost().getUnitPrice();
            totalPrice += total;
            Double percentDiscount = promotionProgram.getPercentageReduction()*1.0 / 100;
            if (!promotionProgram.getAvailableSku().strip().toUpperCase().equals("ALL")){
                if (promotionProgram.getAvailableSku().contains(String.format(";%s;", cartItem.getItemPost().getSalePost().getId().toString()))){
                    discount += (total * percentDiscount > promotionProgram.getReductionAmountMax() ? promotionProgram.getReductionAmountMax(): total * percentDiscount);
                }
            }else {
                discount += (total * percentDiscount > promotionProgram.getReductionAmountMax() ? promotionProgram.getReductionAmountMax(): total * percentDiscount);
            }
        }
        if (discount > promotionProgram.getReductionAmountMax()){
            if (promotionProgram.getReductionAmountMax() > totalPrice){
                discount = totalPrice;
            }else {
                discount = promotionProgram.getReductionAmountMax() * 1.0;
            }
        }
        result.put("agencyID", agencyID);
        result.put("amount", totalPrice);
        result.put("discount", discount);
        return result;
    }

    @Override
    public PromotionCode checkAvailablePromotionCode(String promotionCode) throws RuntimeException{
        List<PromotionCode> promotionCodeList = this.promotionCodeRepository.findByCode(promotionCode);
        if (promotionCodeList.size() == 0){
            throw new RuntimeException("Can not find voucher with code = " + promotionCode);
        }
        PromotionCode promotionCodeObj = promotionCodeList.get(0);
        Date current = new Date();
        if (promotionCodeObj.getEndUsableDate().before(current) || promotionCodeObj.getTotalCurrent() == 0 ||
                promotionCodeObj.getIsPublic() == 0 || promotionCodeObj.getState() == 0){
            throw new RuntimeException("Voucher has expired !!!");
        }
        return promotionCodeObj;
    }

    @Override
    public PromotionProgram getProgramByPromotionCode(String promotionCode) {
        try {
            if (promotionCode == null){
                throw new RuntimeException("can not find voucher");
            }
            List<PromotionCode> promotionCodeList = this.promotionCodeRepository.findByCode(promotionCode);
            if (promotionCodeList.size() == 0){
                throw new RuntimeException("Can not find voucher with code = " + promotionCode);
            }
            PromotionCode promotionCodeObj = promotionCodeList.get(0);
            return promotionCodeObj.getProgram();
        }catch (Exception exx){
            System.out.println(exx.getMessage());
            return null;
        }
    }

    @Override
    public void useVoucher(String promotionCode) {
        try {
            if (promotionCode == null){
                throw new RuntimeException("can not find voucher");
            }
            List<PromotionCode> promotionCodeList = this.promotionCodeRepository.findByCode(promotionCode);
            if (promotionCodeList.size() == 0){
                throw new RuntimeException("Can not find voucher with code = " + promotionCode);
            }
            PromotionCode promotionCodeObj = promotionCodeList.get(0);
            promotionCodeObj.setTotalUse(promotionCodeObj.getTotalUse() + 1);
            promotionCodeObj.setTotalCurrent(promotionCodeObj.getTotalCurrent() - 1);
            this.promotionCodeRepository.save(promotionCodeObj);
        }catch (Exception exx){
            System.out.println(exx.getMessage());
        }
    }
}
