package helloboot.tobyspringboot;

public class ComplexHelloService implements HelloService {
    public String sayHello(String name) {
        return "Complex Hello, " + name;
    }
}
