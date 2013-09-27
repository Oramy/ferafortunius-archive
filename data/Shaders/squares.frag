uniform sampler2D texture;
uniform float direction_x;

void main()
{
	vec2 off;
	
	vec4 color = gl_Color;
	vec4 finalcolor= vec4(0.0);
	finalcolor[0] = (color[0] + color[1] + color[2]) / 3;
	finalcolor[1] = (color[0] + color[1] + color[2]) / 3;
	finalcolor[2] = (color[0] + color[1] + color[2]) / 3;
	finalcolor[3] = color[3];
	gl_FragColor =  finalcolor;
}