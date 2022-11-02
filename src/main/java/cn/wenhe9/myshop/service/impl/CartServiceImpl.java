package cn.wenhe9.myshop.service.impl;

import cn.wenhe9.myshop.dao.CartDao;
import cn.wenhe9.myshop.dao.ProductDao;
import cn.wenhe9.myshop.dao.impl.CartDaoImpl;
import cn.wenhe9.myshop.dao.impl.ProductDaoImpl;
import cn.wenhe9.myshop.domain.entity.Cart;
import cn.wenhe9.myshop.domain.entity.Product;
import cn.wenhe9.myshop.service.CartService;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @description: 购物车业务接口实现类
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public class CartServiceImpl implements CartService {

    private CartDao cartDao;

    private ProductDao productDao;

    public CartServiceImpl(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    @Override
    public void createCart(int uid, String pid) throws SQLException, InvocationTargetException, IllegalAccessException {
        //1.判断商品是否已经存在
        Cart cart = cartDao.hasCart(uid,pid);
        if (cart !=null){
            //已经添加过，修改
            //修改数量
            cart.setNum(cart.getNum()+1);

            cartDao.updateCart(cart);
        }else {
            //3.不存在添加即可
            //1.查询商品
            Product product = productDao.selectProductByPid(pid);

            //完成添加
            cart = Cart.builder()
                    .num(1)
                    .pid(Integer.parseInt(pid))
                    .product(product)
                    .uid(uid)
                    .build();
            //接下来完成插入操作
            cartDao.insertCart(cart);

        }


    }

    @Override
    public List<Cart> findAll(int uid) throws IllegalAccessException, SQLException, InvocationTargetException {
        //根据我们的用户id查询对应购物车里面的商品，声明一个对应的方法
        List<Cart> cartList = cartDao.selectCartByUid(uid);
        return cartList;
    }

    @Override
    public void deleteCartByCid(String cid) throws SQLException {
        cartDao.deleteCartByCid(cid);
    }

    @Override
    public void updateCartByCid(String cid, String price, String cnum) throws SQLException {
        //首先我们要将我们的cnum和price转成对应的大数据类型BigDecimal
        BigDecimal cnumbig = new BigDecimal(cnum);
        BigDecimal pricebig = new BigDecimal(price);
        //进行小计的计算
        BigDecimal count = pricebig.multiply(cnumbig);
        //接下来进行数据库中的修改
        cartDao.updateByCid(count,cnumbig,cid);

    }

    @Override
    public void clearCart(String uid) throws SQLException {
        cartDao.deleteCartByUid(uid);
    }
}
