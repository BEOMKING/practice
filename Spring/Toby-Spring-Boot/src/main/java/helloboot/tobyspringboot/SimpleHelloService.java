package helloboot.tobyspringboot;

import org.springframework.stereotype.Service;

@Service
public class SimpleHelloService implements HelloService {
    private final HelloRepository helloRepository;

    public SimpleHelloService(final HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    public String sayHello(String name) {
        this.helloRepository.increaseHello(name);

        return "Hello, " + name;
    }
}
