package edu.splash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.sun.javafx.application.LauncherImpl;
import rx.Observable;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

class ProgressBeanPostProcessor implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {

	private Application application;
	
	private static final Logger log = LoggerFactory.getLogger(ProgressBeanPostProcessor.class);

	private static final Subject<String, String> beans = ReplaySubject.create();
	
	private static int counter;
	
	
	public ProgressBeanPostProcessor(Application application) {
		super();
		this.application = application;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		
		counter+= 1;
		log.info("the bean name: " + beanName + " № " + counter);
		LauncherImpl.notifyPreloader(application, new CurrentPreloader.ProgressNotification(   ((counter * 100)/32)));
		
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		//log.info("name2 " + beanName);
		return bean;
	}	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info(">>>>>>>>>> " + event.toString());
		beans.onCompleted();
	}

	static Observable<String> observe() {
		return beans;
	}
}
