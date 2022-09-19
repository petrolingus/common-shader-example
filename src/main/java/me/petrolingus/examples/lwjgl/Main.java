package me.petrolingus.examples.lwjgl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Main {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private long window;

    public void run() {
        initialize();
        loop();
    }

    private void initialize() {
        GLFW.glfwInit();
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Common Shader Example", MemoryUtil.NULL, MemoryUtil.NULL);
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            GL11.glBegin(GL11.GL_POLYGON);
               GL11.glVertex2d(-0.8, 0.8);
               GL11.glVertex2d(0.8, 0.8);
               GL11.glVertex2d(0.8, -0.8);
               GL11.glVertex2d(-0.8, -0.8);
            GL11.glEnd();

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }


    public static void main(String[] args) {

        String s = Utils.loadShader("shaders/vertex.shader");
        System.out.println(s);

//        new Main().run();
    }
}