package cn.wenhe9.myshop.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 订单内部的具体商品展示类
 * @author: DuJinliang
 * @create: 2022/10/27
 */
@Getter
@Setter
@Builder
@ToString
public class Item extends BaseEntity {
    /**
     * 订单项的唯一标识
     */
    private int iId;
    /**
     * 订单编号是字符串类型但是也是唯一标识
     */
    private String oId;
    /**
     * 商品的唯一主键
     */
    private int pId;

    /**
     * 产品
     */
    private Product product;
    /**
     * 订单项的小计
     */
    private BigDecimal count;
    /**
     * 订单项的数量
     */
    private int num;
}
