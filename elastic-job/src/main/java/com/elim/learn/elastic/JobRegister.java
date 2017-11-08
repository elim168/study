package com.elim.learn.elastic;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobCoreConfiguration.Builder;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * 注册Job，用于不通过Spring配置文件进行注册，而是动态的从比如是数据库中读取配置信息。
 * 本类提供了两种示例，一种是基于Spring bean的，一种是基于非Spring的。
 * @author Elim
 * 2017年11月8日
 */
public class JobRegister implements BeanDefinitionRegistryPostProcessor {

    private static final String REG_CENTER_BEAN = "regCenter";
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        List<JobTypeConfiguration> jobConfigurations = this.getJobConfigurations();
        jobConfigurations.forEach(config -> {
            JobCoreConfiguration coreConfig = config.getCoreConfig();
            BeanDefinitionBuilder rootBean = BeanDefinitionBuilder.rootBeanDefinition(SpringJobScheduler.class);
            rootBean.setInitMethodName("init");
            rootBean.addConstructorArgValue(BeanDefinitionBuilder.rootBeanDefinition(config.getJobClass()).getBeanDefinition());
            rootBean.addConstructorArgReference(REG_CENTER_BEAN);
            
            LiteJobConfiguration liteJobConfig = LiteJobConfiguration.newBuilder(config).overwrite(true).build();
            rootBean.addConstructorArgValue(liteJobConfig);
            rootBean.addConstructorArgValue(new Object[] {});
            registry.registerBeanDefinition(coreConfig.getJobName(), rootBean.getBeanDefinition());
        });
    }
    
    /**
     * 获取作业配置，真实情况下可以应用于数据库配置
     * @return
     */
    private List<JobTypeConfiguration> getJobConfigurations() {
        List<JobTypeConfiguration> configurations = new ArrayList<>();
        configurations.add(this.newSimpleJob("regist1", RegistSimpleJob.class.getName(), "1 * * * * ?", 1));
        configurations.add(this.newSimpleJob("regist2", RegistSimpleJob.class.getName(), "21 * * * * ?", 1));
        configurations.add(this.newSimpleJob("regist3", RegistSimpleJob.class.getName(), "41 * * * * ?", 1));
        return configurations;
    }
    
    /**
     * 创建一个简单作业
     * @param jobName 作业名称
     * @param jobClass 作业对应的Class
     * @param cron CRON表达式
     * @param shardingTotalCount 总的分片数
     * @return
     */
    private JobTypeConfiguration newSimpleJob(String jobName, String jobClass, String cron, int shardingTotalCount) {
        JobCoreConfiguration coreConfiguration = this.buildCoreConfiguration(jobName, cron, shardingTotalCount);
        return new SimpleJobConfiguration(coreConfiguration, jobClass);
    }

    /**
     * 构造Job的核心配置信息
     * @param jobName 作业名称
     * @param cron CRON表达式
     * @param shardingTotalCount 总的分片数
     * @return
     */
    private JobCoreConfiguration buildCoreConfiguration(String jobName, String cron, int shardingTotalCount) {
        Builder builder = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount);
        builder.failover(true).description("测试自动注册任务");
        return builder.build();
    }
    
    /**
     * 验证非使用Spring的情况下通过API配置作业。
     * @param args
     */
    public static void main(String[] args) {
        new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
    }
    
    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("10.192.48.170:2181", "elastic-job-demo"));
        regCenter.init();
        return regCenter;
    }
    
    private static LiteJobConfiguration createJobConfiguration() {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("test", "0 * * * * ?", 2).build();
        JobTypeConfiguration config = new SimpleJobConfiguration(jobCoreConfiguration, RegistSimpleJob.class.getName());
        return LiteJobConfiguration.newBuilder(config).build();
    }
    
}
