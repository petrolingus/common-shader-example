package me.petrolingus.examples.lwjgl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final int WIDTH = 720;
    private static final int HEIGHT = 720;
    private long window;

    public void run() throws Exception {
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

    private void loop() throws Exception {
        GL.createCapabilities();
        GL11.glClearColor(0.82f, 0.87f, 0.89f, 0.0f);
        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        String vertexShaderPath = "src/main/resources/shaders/vertex.shader";
        String fragmentShaderPath = "src/main/resources/shaders/fragment.shader";
        ShaderProgram shaderProgram = new ShaderProgram(vertexShaderPath, fragmentShaderPath);


        float[] vertices = {
                1.0f,  1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                -1.0f, -1.0f, 0.0f,
                -1.0f,  1.0f, 0.0f,
        };
        int[] indices = {
                0, 1, 3,
                1, 2, 3
        };
        Mesh mesh = new Mesh(vertices, indices);

        int N = 3 * 100;

        // Create FloatBuffer
        float[] positions = new float[N];
        int positionsLength = positions.length;
        for (int i = 0; i < positionsLength; i++) {
            positions[i] = (float) (ThreadLocalRandom.current().nextDouble());
            positions[i] = 0.5f;
        }
        FloatBuffer positionsFloatBuffer = MemoryUtil.memAllocFloat(positionsLength);
        positionsFloatBuffer.put(positions).flip();
        mesh.bufferDataUpdate(positionsFloatBuffer);

        long start = System.nanoTime();

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            long now = System.nanoTime();
            if (now - start > 8_000_000) {
                start = now;
                for (int i = 0; i < positionsLength; i++) {
                    positions[i] += (float) (0.001 * ThreadLocalRandom.current().nextGaussian());
                    if (positions[i] > 1.0) {
                        positions[i] -= 1.0;
                    }
                    if (positions[i] < 0.0) {
                        positions[i] = 1.0f - positions[i];
                    }
                }
                positionsFloatBuffer.put(positions).flip();
                mesh.bufferDataUpdate(positionsFloatBuffer);

                shaderProgram.bind();
                mesh.drawInstances(100);
                shaderProgram.unbind();

                GLFW.glfwSwapBuffers(window);
            }


            GLFW.glfwPollEvents();
        }
    }


    public static void main(String[] args) throws Exception {
        new Main().run();
    }
}