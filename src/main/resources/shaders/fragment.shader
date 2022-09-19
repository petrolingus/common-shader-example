#version 330

in vec2 pos;

out vec4 fragColor;

vec3 rgb(float r, float g, float b) {
    return vec3(r / 255.0, g / 255.0, b / 255.0);
}

vec4 circle(vec2 uv, vec2 center, float rad, vec3 color) {
    float dx = uv.x - center.x;
    float dy = uv.y - center.y;
    dx = (abs(dx) > 0.5 * 720.0) ? dx - 720.0 * sign(dx) : dx;
    dy = (abs(dy) > 0.5 * 720.0) ? dy - 720.0 * sign(dy) : dy;
    float d = sqrt(dx * dx + dy * dy) - rad;
    float t = clamp(d, 0.0, 1.0);
    return vec4(color, 1.0 - t);
}

void main()
{
    vec2 iResolution = vec2(720.0, 720.0);
    vec2 uv = gl_FragCoord.xy;
    vec2 center = iResolution.xy * pos;
    float radius = 0.02 * iResolution.y;

    vec3 red = rgb(225.0, 95.0, 60.0);
    vec4 layer2 = circle(uv, center, radius, red);

    fragColor = layer2;
}