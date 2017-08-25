package com.shiro.dao.db.type;

import com.shiro.entity.DO.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wuyiming on 2017/8/24.
 */
@Repository
public interface TypeMapper {
    Type getTypeById(String typeId);

    List<Type> getTypeByCategory(String categoryId);

    List<Type> getAllType();

    int insertType(Type type);

    int updateTypeById(Type type);
}
