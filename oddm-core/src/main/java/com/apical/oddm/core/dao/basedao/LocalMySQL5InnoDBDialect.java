package com.apical.oddm.core.dao.basedao;

import java.sql.Types;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.type.StandardBasicTypes;

/**
 * 自定义数据库操作方言
 * @author lgx
 * 2016-2-17
 */
public class LocalMySQL5InnoDBDialect extends MySQL5InnoDBDialect {

	public LocalMySQL5InnoDBDialect() {
        super();
        //可以实现TINYINT自动转换为INTEGER
        registerHibernateType(Types.TINYINT, StandardBasicTypes.INTEGER.getName());
        //下面这个慎用,不能用，否则全局了
       // registerHibernateType(Types.DECIMAL, StandardBasicTypes.INTEGER.getName());//给public List<OrderInfoYearSellerVo> getOrderInfoYearVoList(String year);

    }
}
