package com.hollomyfoolish.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.HttpSessionMutexListener;

import com.hollomyfoolish.bf.BeanFactory;
import com.hollomyfoolish.web.ctxconfig.SecurityConfig;

public class WebAppInitializer implements WebApplicationInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebAppInitializer.class);
	
	private static final String DEFAULT_SPRING_PROFILE = "production";
	private static final String CHAR_ENCODING_FILTER_NAME = "characterEncodingFilter";
	private static final String COMPONENT_NAME = "bfs";
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		addServiceFilter(servletContext);
        configSessionListener(servletContext);
        addSpringSessionMutextListener(servletContext);
        initSpringContext(servletContext);
        configServletContext(servletContext);
        addServletHandlers(servletContext);
	}
	
	private void addServletHandlers(ServletContext servletContext) {
		addSpringDispatcher(servletContext);
	}

	private void addServiceFilter(final ServletContext servletContext) {
		addSpringSessionRepositoryFilter(servletContext);
		addUnhandledExceptionFilter(servletContext);
        addCharacterEncodingFilter(servletContext);
        addCORSFilter(servletContext);
        addLogonFilter(servletContext);
        addSpringSecurityFilter(servletContext);
    }

	private void addSpringSessionRepositoryFilter(ServletContext servletContext) {
//    	DelegatingFilterProxy filter = new DelegatingFilterProxy("springSessionRepositoryFilter");
//		servletContext.addFilter("springSessionRepositoryFilter", filter).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
	}
	
	private void addSpringSecurityFilter(final ServletContext servletContext) {
		DelegatingFilterProxy filter = new DelegatingFilterProxy("springSecurityFilterChain");
		servletContext.addFilter("springSecurityFilterChain", filter).addMappingForUrlPatterns(null, false, "/*");
	}

	private void addLogonFilter(ServletContext servletContext) {
		// TODO
	}

	private void addUnhandledExceptionFilter(ServletContext servletContext) {
		// TODO
	}

    private void addCharacterEncodingFilter(final ServletContext servletContext) {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        final javax.servlet.FilterRegistration.Dynamic filter = servletContext.addFilter(CHAR_ENCODING_FILTER_NAME,
                characterEncodingFilter);
        if (filter != null) {
            filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
        }
    }

    private void addCORSFilter(ServletContext servletContext) {
    	// TODO
    }

    protected void configSessionListener(ServletContext servletContext) {
        // TODO
    	// servletContext.addListener(SessionListener.class);
    }

    protected void initSpringContext(ServletContext servletContext) {
        addSpringContextLoaderListener(servletContext);
    }

    private void addSpringSessionMutextListener(final ServletContext servletContext) {
        servletContext.addListener(HttpSessionMutexListener.class);
    }

    protected void configAppContext(AnnotationConfigWebApplicationContext appContext) {
    	// TODO add root bean config here, such as service/dao/ or any other common beans
    	appContext.setDisplayName("bfs");
		appContext.register(BeanFactory.class);
		appContext.register(SecurityConfig.class);
//		appContext.register(SpringHttpSessionConfig.class);
//		appContext.register(Oauth2ServerConfig.class);
    }

    protected void configServletContext(ServletContext servletContext) {
    	configSession(servletContext);
    	setContextValue(servletContext);
    }

    private void configSession(ServletContext servletContext) {
    	// TODO
		// servletContext.getSessionCookieConfig().setSecure(true);
	}
    
	private void addSpringDispatcher(ServletContext servletContext) {
		Dynamic springDispatcher = servletContext.addServlet("SpringDispatcherServlet", DispatcherServlet.class);
		springDispatcher.addMapping("/api/*");
		springDispatcher.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
		springDispatcher.setInitParameter("contextInitializerClasses", "com.hollomyfoolish.web.NlpAuthSpringWebContext");
		springDispatcher.setLoadOnStartup(1); 
	}

	private void setContextValue(ServletContext servletContext) {
		servletContext.setInitParameter("spring.profiles.default", "dev");
		servletContext.setInitParameter("webAppRootKey", "bfs-service");
	}

    private void addSpringContextLoaderListener(final ServletContext servletContext) {
        servletContext.addListener(new ContextLoaderListener() {
            @Override
            protected WebApplicationContext createWebApplicationContext(ServletContext sc) {
                final AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
                registerApplicationContextListeners(appContext);
                configAppContext(appContext);
                configSpringProfile(appContext);
                appContext.registerShutdownHook();
                return appContext;
            }
            
            @Override
            public void contextDestroyed(ServletContextEvent sce){
            	try{
        			// TODO do something before destroy spring context
            		// WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        		}catch(Throwable e){
        			LOGGER.error("fail to get WebApplicationContext", e);
        		}
            	super.contextDestroyed(sce);
            }
        });
    }

    private void registerApplicationContextListeners(final AnnotationConfigWebApplicationContext appContext) {
    	// TODO add listener you need
    	// appContext.addApplicationListener(new DefaultAppContextListener());
    }

    private void configSpringProfile(final AnnotationConfigWebApplicationContext appContext) {
        appContext.getEnvironment().setDefaultProfiles(DEFAULT_SPRING_PROFILE);
        if (null == appContext.getEnvironment().getActiveProfiles()
                || 0 == appContext.getEnvironment().getActiveProfiles().length) {
            appContext.getEnvironment().setActiveProfiles(DEFAULT_SPRING_PROFILE);
        }
    }

    protected String getComponentName() {
        return COMPONENT_NAME;
    }
}
