package com.example.demo.task;

import com.example.demo.bean.User;
import com.example.demo.dao.UserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: demo
 * @description: 初始化数据
 * @author: qilei
 * @create: 2021/5/11
 */
@Component
public class InitDataConfig {
    public static Map<String, Boolean> TABLE_NAME_MAP = new ConcurrentHashMap<String, Boolean>();
    @Resource
    private UserMapper mapper;

    private static InitDataConfig initDataConfig;

    /**
     * @PostConstruct注解一个方法来完成初始化，@PostConstruct注解的方法将会在依赖注入完成后被自动调用。
     *
     * 加载顺序：Constructor >> @Autowired >> @PostConstruct
     */
    @PostConstruct
    public void init() {
        initDataConfig = this;
        initDataConfig.mapper = this.mapper;
    }

    public static void setTableNameMap(){
        Map<String, Object> param = new HashMap<>();
        param.put("tableName","user");
        List<User> list =  initDataConfig.mapper.queryTableName(param);
        list.forEach(user -> TABLE_NAME_MAP.put(user.getTableName(),Boolean.TRUE));
    }
}
