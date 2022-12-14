package me.petrolingus.examples.lwjgl;

import org.lwjgl.opengl.GL30;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private final int programId;

    private final Map<String, Integer> uniforms;

    public ShaderProgram(String vertexShaderPath, String fragmentShaderPath) throws Exception {
        programId = GL30.glCreateProgram();
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }
        uniforms = new HashMap<>();
        createShaders(vertexShaderPath, fragmentShaderPath);
    }

    private void createShaders(String vertexShaderPath, String fragmentShaderPath) throws Exception {

        // Create vertex shader
        int vertexShaderId = GL30.glCreateShader(GL30.GL_VERTEX_SHADER);
        if (vertexShaderId == 0) {
            throw new Exception("Error creating shader. Type: " + GL30.GL_VERTEX_SHADER);
        }
        compileShader(vertexShaderPath, vertexShaderId);

        // Create fragment shader
        int fragmentShaderId = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER);
        if (fragmentShaderId == 0) {
            throw new Exception("Error creating shader. Type: " + GL30.GL_FRAGMENT_SHADER);
        }
        compileShader(fragmentShaderPath, fragmentShaderId);

        // Link shaders to program
        GL30.glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
        GL30.glDetachShader(programId, vertexShaderId);
        GL30.glDetachShader(programId, fragmentShaderId);
        GL30.glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    private void compileShader(String shaderPath, int shaderId) throws Exception {
        String fragmentShaderCode = Utils.loadShader(shaderPath);
        GL30.glShaderSource(shaderId, fragmentShaderCode);
        GL30.glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }
        GL30.glAttachShader(programId, shaderId);
    }

    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = GL30.glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void bind() {
        GL30.glUseProgram(programId);
    }

    public void unbind() {
        GL30.glUseProgram(0);
    }
}
