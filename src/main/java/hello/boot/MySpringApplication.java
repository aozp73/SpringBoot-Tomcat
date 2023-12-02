package hello.boot;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.List;

public class MySpringApplication {
    public static void run(Class configClass, String[] args) {
        System.out.println("MySpringBootApplication.run args=" + List.of(args));

        // 1. ��Ĺ ����
        Tomcat tomcat = new Tomcat();
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        // 2. ������ �����̳� ����
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(configClass);

        // 3. ������ MVC ����ó ���� ����, ������ �����̳� ����
        DispatcherServlet dispatcher = new DispatcherServlet(appContext);

        // 4. ����ó ���� ���
        Context context = tomcat.addContext("", "/");
        tomcat.addServlet("", "dispatcher", dispatcher);
        context.addServletMappingDecoded("/", "dispatcher");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
