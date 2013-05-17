uniform sampler2D texture;
uniform float offset;
uniform float direction_x;

void main()
{
	vec2 off;
	
	if(direction_x == 1.0)
		off = vec2(offset, 0.0);
	else
		off = vec2(0.0, offset);
	vec4 color = gl_Color * (
			texture2D(texture, gl_TexCoord[0].xy + off * 1.0)	* 0.06 + 
			texture2D(texture, gl_TexCoord[0].xy + off * 0.75)	* 0.09 +
			texture2D(texture, gl_TexCoord[0].xy + off * 0.5)	* 0.12 +
			texture2D(texture, gl_TexCoord[0].xy + off * 0.25)	* 0.15 +
			
			texture2D(texture, gl_TexCoord[0].xy)	* 0.16 +
				
			texture2D(texture, gl_TexCoord[0].xy - off * 1.0) 	* 0.06 +
			texture2D(texture, gl_TexCoord[0].xy - off * 0.75)	* 0.09 +
			texture2D(texture, gl_TexCoord[0].xy - off * 0.5)	* 0.12 +
			texture2D(texture, gl_TexCoord[0].xy - off * 0.25)	* 0.15 );
	vec4 finalcolor= vec4(0.0);
	finalcolor[0] = color[0];
	finalcolor[1] = color[1];
	finalcolor[2] = color[2];
	finalcolor[3] = gl_Color[3];
	gl_FragColor =  finalcolor;
}