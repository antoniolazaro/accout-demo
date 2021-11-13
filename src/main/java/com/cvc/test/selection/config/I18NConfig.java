package com.cvc.test.selection.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class I18NConfig{

    @Bean
    public LocaleResolver localeResolver() {

        SessionLocaleResolver localResolver=new SessionLocaleResolver();
        localResolver.setDefaultLocale(Locale.US);
        return localResolver;
    }

    @Bean
    public MessageSource messageResource() {
        ResourceBundleMessageSource messageBundleResrc=new ResourceBundleMessageSource();
        messageBundleResrc.setBasename("text");
        messageBundleResrc.setUseCodeAsDefaultMessage(true);

        System.out.println(messageBundleResrc.getMessage("country", null, Locale.US));
        return messageBundleResrc;
    }
}
