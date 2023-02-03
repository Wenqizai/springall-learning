package com.wenqi.dao.jdbc;

import java.util.List;

/**
 * @author liangwenqi
 * @date 2023/2/3
 */
public interface IDAO {
    int updateSomething(String sql);

    List<DaoPojo> selectList(String sql);
}
