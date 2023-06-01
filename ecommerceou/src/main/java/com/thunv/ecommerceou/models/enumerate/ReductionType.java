package com.thunv.ecommerceou.models.enumerate;

public enum ReductionType {
    DECREASE_BY_PERCENTAGE, DECREASE_BY_AMOUNT;
    public Integer getValue(){
        switch (this){
            case DECREASE_BY_AMOUNT:
                return 1;
            case DECREASE_BY_PERCENTAGE:
                return 2;
            default:
                return 0;
        }
    }
    public static ReductionType getValueByInt(Integer input){
        switch (input){
            case 1:
                return ReductionType.DECREASE_BY_AMOUNT;
            case 2:
                return ReductionType.DECREASE_BY_PERCENTAGE;
            default:
                return null;
        }
    }
}
