package com.example.sso.configuration;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.example.sso.formatter.NameFormatter;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private LocaleChangeInterceptor localeChangeInterceptor;

	@Autowired
	private MessageSource messageSource;

	@Bean
	public ViewResolver htmlViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
		resolver.setContentType("text/html");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(ArrayUtils.toArray("*.html"));
		return resolver;
	}

	@Bean
	public ViewResolver javascriptViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(javascriptTemplateResolver()));
		resolver.setContentType("application/javascript");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(ArrayUtils.toArray("*.js"));
		return resolver;
	}

	@Bean
	public ViewResolver plainViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(plainTemplateResolver()));
		resolver.setContentType("text/plain");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(ArrayUtils.toArray("*.txt"));
		return resolver;
	}

	public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addDialect(new LayoutDialect(new GroupingStrategy()));
		engine.addDialect(new Java8TimeDialect());
		engine.setTemplateResolver(templateResolver);
		engine.setTemplateEngineMessageSource(this.messageSource);
		return engine;
	}

	public SpringResourceTemplateResolver htmlTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(this.applicationContext);
		resolver.setPrefix("classpath:templates/");
		resolver.setSuffix(".html");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}

	private ITemplateResolver javascriptTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(this.applicationContext);
		resolver.setPrefix("classpath:static/js/");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
		return resolver;
	}

	private ITemplateResolver plainTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:static/txt/");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.TEXT);
		return resolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.localeChangeInterceptor);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**", "/img/**", "/css/**", "/js/**").addResourceLocations(
				"classpath:/META-INF/resources/webjars/", "classpath:/static/img/", "classpath:/static/css/",
				"classpath:/static/js/");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new NameFormatter());
	}

}
