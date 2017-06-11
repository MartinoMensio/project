package it.polito.ai.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import it.polito.ai.project.web.controller.formatters.CarSharingServiceFormatter;
import it.polito.ai.project.web.controller.formatters.EducationLevelFormatter;
import it.polito.ai.project.web.controller.formatters.EmploymentFormatter;
import it.polito.ai.project.web.controller.formatters.FuelFormatter;
import it.polito.ai.project.web.controller.formatters.TravelDocumentFormatter;
import it.polito.ai.project.web.interceptors.CompleteProfileInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
  @Autowired 
  private CompleteProfileInterceptor completeProfileInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
	  registry.addInterceptor(completeProfileInterceptor)
	  			.addPathPatterns("/**")
	  			.excludePathPatterns("/updateProfile");
  }
  
  @Override
  public void addFormatters(FormatterRegistry registry) {
	  super.addFormatters(registry);
	  registry.addFormatter(carSharingServiceFormatter());
	  registry.addFormatter(educationLevelFormatter());
	  registry.addFormatter(employmentFormatter());
	  registry.addFormatter(fuelFormatter());
	  registry.addFormatter(travelDocumentFormatter());
  }
  
  @Bean
  public CarSharingServiceFormatter carSharingServiceFormatter() {
	  return new CarSharingServiceFormatter();
  }
  
  @Bean
  public EducationLevelFormatter educationLevelFormatter() {
	  return new EducationLevelFormatter();
  }
  
  @Bean
  public EmploymentFormatter employmentFormatter() {
	  return new EmploymentFormatter();
  }
  
  @Bean
  public FuelFormatter fuelFormatter() {
	  return new FuelFormatter();
  }

  @Bean
  public TravelDocumentFormatter travelDocumentFormatter() {
	  return new TravelDocumentFormatter();
  }
}
