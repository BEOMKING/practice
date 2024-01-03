package helloboot.tobyspringboot;

public interface HelloRepository {
    Hello findHello(String name);

    void increaseHello(String name);

    default int countOf(String name) {
        Hello hello = findHello(name);
        return hello == null ? 0 : hello.getCount();
    }
}
