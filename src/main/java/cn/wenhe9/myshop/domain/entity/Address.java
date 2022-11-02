package cn.wenhe9.myshop.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description: 地址类
 * @author: DuJinliang
 * @create: 2022/10/27
 */
@Getter
@Setter
@Builder
@ToString
public class Address extends BaseEntity {

    /**
     * 地址实体的唯一主键列
     */
    private int aid;

    /**
     * 用户实体的主键属性
     */
    private int uid;

    /**
     * 地址的收件人
     */
    private String name;

    /**
     * 收件人电话
     */
    private String phone;

    /**
     * 收货人详细地址
     */
    private String detail;

    /**
     * 是否是默认地址 0 不是 1是默认地址
     */
    private int state;
}
