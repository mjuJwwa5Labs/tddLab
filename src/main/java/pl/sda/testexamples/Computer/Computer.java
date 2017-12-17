package pl.sda.testexamples.Computer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Computer {

    private Keyboard keyboard;
    private Display display;

    public void operate() {
        String message = keyboard.read();
        display.display(message);
    }
}
