package jpabook.jpashop2;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Jpashop2Application {

	public static void main(String[] args) { SpringApplication.run(Jpashop2Application.class, args);


	}

    @Bean
    Hibernate5JakartaModule hibernate5JakartaModule() {
         return new Hibernate5JakartaModule();
        // 기본적으로 초기화 된 프록시 객체만 노출,
        // 초기화 되지 않은 프록시 객체는 노출하지 않음.

        //2)
//        Hibernate5JakartaModule module = new Hibernate5JakartaModule();
//        module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING,true);
//        return module;
        // Lazy 로딩된 것을 강제로 가져옴. (지연로딩 되는 것을) -> 강제 지연 로딩 옵션.

    }

}
