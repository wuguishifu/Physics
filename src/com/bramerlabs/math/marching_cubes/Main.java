package com.bramerlabs.math.marching_cubes;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;

public class Main implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private Renderer renderer;
    private Vector3f lightPosition = new Vector3f(0, 100f, 0);

    private RenderObject object;

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        Thread main = new Thread(this, "Marching Cubes");
        main.start();
    }

    public void run() {
        init();
        while (!window.shouldClose()) {
            update();
            render();
        }
    }

    public void init() {
        window.create();
        shader = new Shader(
                "shaders/default/vertex.glsl",
                "shaders/default/fragment.glsl"
        ).create();
        renderer = new Renderer(window, lightPosition);
        camera = new Camera(new Vector3f(0), new Vector3f(0), input);
        camera.setFocus(new Vector3f(0));
        camera.setDistance(20.0f);

        object = MarchingCubes.createObject();
        object.createMesh();
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        camera.updateArcball();
    }

    public void render() {
//        GL11.glCullFace(GL11.GL_FRONT);
        GL11.glDisable(GL11.GL_CULL_FACE);
        renderer.renderMesh(object, camera, shader, Renderer.COLOR);
        window.swapBuffers();
    }

}
