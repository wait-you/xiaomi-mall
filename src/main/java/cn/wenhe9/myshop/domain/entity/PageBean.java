package cn.wenhe9.myshop.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @description: 分页对象
 * @author: DuJinliang
 * @create: 2022/10/27
 */

@Getter
@Setter
public class PageBean<T> {

    /**
     * 展示的数据
     */
    private List<T> list;

    /**
     * 当前页数
     */
    private int currentPage;

    /**
     * 页容量，每页显示的数据条数
     */
    private int pageSize;

    /**
     * 总条数
     */
    private long totalCount;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 不传入总页数（构造方法）
     */
    public PageBean(List<T> list, int currentPage, int pageSize, long totalCount) {
        this.list = list;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        //需要进行一个运算
        //13条数据  每页显示5   3（Math.ceil向下舍余）（totalCount*1.0转成double类型）
        return (int) Math.ceil(totalCount*1.0/pageSize);
    }
}
