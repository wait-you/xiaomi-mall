package cn.wenhe9.myshop.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 订单类
 * @author: DuJinliang
 * @create: 2022/10/27
 */

@Getter
@Setter
@Builder
@ToString
public class Orders extends BaseEntity {

    /**
     * 订单编号是字符串类型但是也是唯一标识
     */
    private String oId;

    /**
     * 用户实体的主键属性
     */
    private int uId;

    /**
     * 地址实体的唯一主键列
     */
    private int aId;

    /**
     * 订单的地址信息
     */
    private Address address;

    /**
     * 订单总金额
     */
    private BigDecimal count;

    /**
     * 订单的详细时间
    */
    private Date time;

    /**
     * 订单状态 0 未付款，1已经付款未发货 2发货待收货 3 收货待评价 4订单完成 5 退货状态
     */
    private int state;

    /**
     * 订单内部的具体商品展示项
     */
    private List<Item> items;
}
