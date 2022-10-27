package cn.wenhe9.myshop.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description: 类别类
 * @author: DuJinliang
 * @create: 2022/10/27
 */

@Getter
@Setter
@Builder
@ToString
public class Type  extends BaseEntity {

    /**
     * 类别的主键id
     */
    private int  tId;

    /**
     * 类别的名称
     */
    private String name;

    /**
     * 类别的描述
     */
    private String info;
}
