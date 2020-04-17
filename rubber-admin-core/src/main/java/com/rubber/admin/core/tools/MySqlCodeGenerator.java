package com.rubber.admin.core.tools;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * mybatis-plus 代码生成工具
 * @author luffyu
 * Created on 2019-10-31
 */
public class MySqlCodeGenerator {

    /**
     *  模块名称
     */
    private static final String MODEL_NAME = "rubber-admin-core";
    /**
     * 作者名称
     */
    private static final String AUTHOR = "luffyu-auto";
    /**
     * 链接的数据库配置
     */
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/rubber_admin?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    /**
     * 生成文件所在的包名称
     */
    private static final String PACKAGE_PARENT = "com.rubber.admin.core.authorize";

    /**
     * entity继承的baseEntity地址
     */
    private static final String BASE_ENTITY_CLASS = "com.rubber.admin.core.base.BaseEntity";
    /**
     * serviceImpl继承的serviceImpl地址
     */
    private static final String BASE_SERVICE_IMPL = "com.rubber.admin.core.base.BaseAdminService";
    //service继承的service地址
    //private static final String baseService = "com.coocaa.lottery.platform.pojo.base.IBaseService";


    public static void main(String[] args) {
        createCode("auth_group_config");
    }


    /**
     * 根据表名称生成 文件
     * @param tableName 表名称数组
     */
    private static void createCode(String... tableName){
        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        //全局变量配置
        GlobalConfig gc = new GlobalConfig();

        String projectPath = MODEL_NAME;
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(AUTHOR);
        //是否打开输出文件
        gc.setOpen(false);
        //是否覆盖已经存在的文件
        gc.setFileOverride(false);
        mpg.setGlobalConfig(gc);


        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(JDBC_URL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername(USER_NAME);
        dataSourceConfig.setPassword(PASSWORD);
        mpg.setDataSource(dataSourceConfig);

        //包名 配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE_PARENT);
        pc.setXml("mapper");

        mpg.setPackageInfo(pc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        strategy.setSuperEntityClass(BASE_ENTITY_CLASS);
        strategy.setSuperServiceImplClass(BASE_SERVICE_IMPL);
        //strategy.setSuperServiceClass(baseService);
        //是否开启 lombok 开启之后 文件中没有get set方法
        strategy.setEntityLombokModel(true);
        //对于controller 是否开启 @RestController注解
        strategy.setRestControllerStyle(true);

        strategy.setInclude(tableName);
        //strategy.setSuperEntityColumns(keyId);
        //乐观锁字段
        //strategy.setVersionFieldName("version");

        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
