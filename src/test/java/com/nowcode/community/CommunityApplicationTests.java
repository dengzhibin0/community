package com.nowcode.community;

import com.nowcode.community.dao.AlphaDao;
import com.nowcode.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Autowired
	@Qualifier("alphaHibernate")
	private AlphaDao alphaDao;

	@Resource
	private AlphaService alphaService;

	@Test
	void contextLoads() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

	@Test
	public void testApplicationContext(){
		System.out.println(applicationContext);

		AlphaDao alphaDao=applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());

		alphaDao=applicationContext.getBean("alphaHibernate",AlphaDao.class);
		System.out.println(alphaDao.select());
	}

	@Test
	public void testBeanManagement(){
		AlphaService alphaService=applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
	}

	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat=applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}

	@Test
	public void testDI(){
		System.out.println(alphaDao);
		System.out.println(alphaService);
	}
}
