package com.bramerlabs.physics.heat;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;

public class Heat implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private Renderer renderer;
    private Vector3f lightPosition = new Vector3f(0, 100f, 0);

    public static void main(String[] args) {
        new Heat().start();
    }

    public void start() {
        Thread main = new Thread(this, "Heat");
    }

    public void run() {
        init();
        while (!window.shouldClose()) {
            update();
            render();
        }
    }

    private void init() {
        window.create();
        shader = new Shader("shaders/default/vertex.glsl",
                "shaders/default/fragment.glsl").create();

    }

    private void update() {

    }

    private void render() {

    }

}