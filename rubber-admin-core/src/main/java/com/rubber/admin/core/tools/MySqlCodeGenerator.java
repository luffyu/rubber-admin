package com.rubber.admin.core.tools;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * Created by luffyu on 6/11/2018.
 * mybatis-plus 代码生成工具
 */
public class MySqlCodeGenerator {

    //模块名称
    private static final String modelName = "rubber-admin-core";
    //作者名称
    private static final String author = "luffyu-auto";
    //链接的数据库配置
    private static final String jdbcUrl = "jdbc:mysql://127.0.0.1/rubber-admin?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true";
    private static final String dbUserName = "root";
    private static final String dbPassword = "root";

    //生成文件所在的包名称
    private static final String packageParent = "com.rubber.admin.core";

    //entity继承的baseEntity地址
    private static final String baseEntityClass = "com.rubber.admin.core.base.BaseEntity";
    //serviceImpl继承的serviceImpl地址
    private static final String baseServiceImpl = "com.rubber.admin.core.base.BaseService";
    //service继承的service地址
    //private static final String baseService = "com.coocaa.lottery.platform.pojo.base.IBaseService";


    public static void main(String[] args) {
        createCode("sys_dept","sys_menu","sys_role","sys_role_dept","sys_role_menu","sys_user","sys_user_role");
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

        String projectPath = System.getProperty("user.dir") + "/"+modelName;
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(author);
        //是否打开输出文件
        gc.setOpen(false);
        //是否覆盖已经存在的文件
        gc.setFileOverride(false);
        mpg.setGlobalConfig(gc);


        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(jdbcUrl);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername(dbUserName);
        dataSourceConfig.setPassword(dbPassword);
        mpg.setDataSource(dataSourceConfig);

        //包名 配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageParent);
        pc.setXml("mapper");

        mpg.setPackageInfo(pc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        strategy.setSuperEntityClass(baseEntityClass);
        strategy.setSuperServiceImplClass(baseServiceImpl);
        //strategy.setSuperServiceClass(baseService);


        //是否开启 lombok 开启之后 文件中没有get set方法
        strategy.setEntityLombokModel(false);
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
