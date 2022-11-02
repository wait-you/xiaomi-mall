package cn.wenhe9.myshop.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 产品类
 * @author: DuJinliang
 * @create: 2022/10/27
 */

@Getter
@Setter
@Builder
@ToString
public class Product extends BaseEntity {

    /**
     * 商品的唯一主键
     */
    private int pid;

    /**
     * 类别的主键id
     */
    private int tid;

    /**
     * 商品的名称
     */
    private String name;

    /**
     * 商品的上架时间！ 数据库date --> java.util.Date
     */
    private Date time;

    /**
     * 商品的图片名称
     */
    private String image;

    /**
     * 商品的热门指数
     */
    private int state;

    /**
     * 商品的描述
     */
    private String info;

    /**
     * 价格
     */
    private BigDecimal price;
}
