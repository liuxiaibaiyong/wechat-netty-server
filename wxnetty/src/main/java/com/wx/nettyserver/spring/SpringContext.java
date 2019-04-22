package com.wx.nettyserver.spring;

import java.lang.String;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

public enum SpringContext implements ApplicationContext{
    SINGLETON("classpath*:config/applicationContext.xml");
    private ApplicationContext ctx;

    SpringContext(String path) {
        try {
            ctx = new ClassPathXmlApplicationContext(path);
        } catch (BeansException e) {
            ctx = new FileSystemXmlApplicationContext(path);
        }


    }

    public BeanFactory getParentBeanFactory() {
        return ctx.getParentBeanFactory();
    }

    public boolean containsLocalBean(String s) {
        return ctx.containsLocalBean(s);
    }

    public boolean containsBeanDefinition(String s) {
        return ctx.containsBeanDefinition(s);
    }

    public int getBeanDefinitionCount() {
        return ctx.getBeanDefinitionCount();
    }

    public String[] getBeanDefinitionNames() {
        return ctx.getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(ResolvableType resolvableType) {
        return new String[0];
    }

    public String[] getBeanNamesForType(Class aClass) {
        return ctx.getBeanNamesForType(aClass) ;
    }

    public String[] getBeanNamesForType(Class aClass, boolean b, boolean b1) {
        return ctx.getBeanNamesForType(aClass, b, b1);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> aClass) throws BeansException {
        return ctx.getBeansOfType(aClass);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> aClass, boolean b, boolean b1) throws BeansException {
        return ctx.getBeansOfType(aClass,b,b1);
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> aClass) {
        return new String[0];
    }

    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> aClass) throws BeansException {
        return ctx.getBeansWithAnnotation(aClass) ;
    }

    public <A extends Annotation> A findAnnotationOnBean(String s, Class<A> aClass) {
        return ctx.findAnnotationOnBean(s,aClass);
    }

    public Object getBean(String s) throws BeansException {
        return ctx.getBean(s);
    }

    public <T> T getBean(String s, Class<T> aClass) throws BeansException {
        return ctx.getBean(s,aClass);
    }

    public <T> T getBean(Class<T> aClass) throws BeansException {
        return ctx.getBean(aClass);
    }

    public Object getBean(String s, Object... objects) throws BeansException {
        return ctx.getBean(s,objects);
    }

    @Override
    public <T> T getBean(Class<T> aClass, Object... objects) throws BeansException {
        return ctx.getBean(aClass,objects);
    }

    public boolean containsBean(String s) {
        return ctx.containsBean(s);
    }

    public boolean isSingleton(String s) throws NoSuchBeanDefinitionException {
        return ctx.isSingleton(s);
    }

    public boolean isPrototype(String s) throws NoSuchBeanDefinitionException {
        return ctx.isPrototype(s);
    }

    @Override
    public boolean isTypeMatch(String s, ResolvableType resolvableType) throws NoSuchBeanDefinitionException {
        return ctx.isTypeMatch(s,resolvableType);
    }

    public boolean isTypeMatch(String s, Class aClass) throws NoSuchBeanDefinitionException {
        return ctx.isTypeMatch(s,aClass);
    }

    public Class<?> getType(String s) throws NoSuchBeanDefinitionException {
        return ctx.getType(s);
    }

    public String[] getAliases(String s) {
        return ctx.getAliases(s);
    }

    public void publishEvent(ApplicationEvent applicationEvent) {
      ctx.publishEvent(applicationEvent);
    }

    @Override
    public void publishEvent(Object o) {
      ctx.publishEvent(o);
    }

    public String getMessage(String s, Object[] objects, String s1, Locale locale) {
        return ctx.getMessage(s, objects, s1,locale);
    }

    public String getMessage(String s, Object[] objects, Locale locale) throws NoSuchMessageException {
        return ctx.getMessage(s, objects, locale) ;
    }

    public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) throws NoSuchMessageException {
        return ctx.getMessage(messageSourceResolvable, locale);
    }

    public Resource[] getResources(String s) throws IOException {
        return ctx.getResources(s);
    }

    public Resource getResource(String s) {
        return ctx.getResource(s);
    }

    public ClassLoader getClassLoader() {
        return ctx.getClassLoader();
    }

    public String getId() {
        return ctx.getId();
    }

    @Override
    public String getApplicationName() {
        return ctx.getApplicationName();
    }

    public String getDisplayName() {
        return ctx.getDisplayName();
    }

    public long getStartupDate() {
        return ctx.getStartupDate();
    }

    public ApplicationContext getParent() {
        return ctx.getParent();
    }

    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return ctx.getAutowireCapableBeanFactory();
    }

    @Override
    public Environment getEnvironment() {
        return ctx.getEnvironment();
    }
}