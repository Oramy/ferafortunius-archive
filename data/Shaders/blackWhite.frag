uniform sampler2D texture;
uniform float direction_x;

void main()
{
	vec2 off;
	
	vec4 color = gl_Color * texture2D(texture, gl_TexCoord[0].xy);
	vec4 finalcolor= vec4(0.0);
	finalcolor[0] = color[0];
	finalcolor[1] = color[1];
	finalcolor[2] = color[2];
	finalcolor[3] = color[3];
	gl_FragColor =  finalcolor;
}