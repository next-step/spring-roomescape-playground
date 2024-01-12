package roomescape.domain.bean;

import static java.util.Locale.ENGLISH;

import org.springframework.format.Printer;

public class Hello {
    String name;
    Printer printer;

    public Hello() {
    }

    public Hello(String name, Printer printer) {
        this.name = name;
        this.printer = printer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public String sayHello() {
        return "Hello " + this.name;
    }

    public void print() {
        this.printer.print(sayHello(), ENGLISH);
    }
}
