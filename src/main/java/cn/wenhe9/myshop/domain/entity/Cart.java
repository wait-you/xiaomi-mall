package cn.wenhe9.myshop.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 购物车类
 * @author: DuJinliang
 * @create: 2022/10/27
 */

@Getter
@Setter
@Builder
@ToString
public class Cart extends BaseEntity {

    /**
     * 购物车的唯一标识
     */
    private int cid;

    /**
     * 用户实体的主键属性
     */
    private int uid;

    /**
     * 商品的唯一主键
     */
    private int pid;

    /**
     * 产品
     */
    private Product product;

    /**
     * 购物车商品数量
     */
    private int num;

    /**
     * 购物车小计
     */
    private BigDecimal count;

    public BigDecimal getCount() {

        BigDecimal price = product.getPrice();
        BigDecimal bigDecimal = new BigDecimal(num);

        return price.multiply(bigDecimal);
    }

}
