#version 400 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in vec4 shadowCoords;
in float visibility;


flat out vec4 out_Color;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform sampler2D shadowMap;



uniform vec3 lightColor[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;



void main(void){

	float objectNearestLight = texture(shadowMap, shadowCoords.xy).r;
	float lightFactor = 1.0;
	if(shadowCoords.z > objectNearestLight)
	{
		lightFactor = 1.0 - (shadowCoords.w * 0.3);
	}




	vec4 blendMapColor = texture(blendMap, pass_textureCoordinates);
	float backgroundTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
	vec2 tileCoords = pass_textureCoordinates * 32;
	vec4 backgroundTextureColor = texture(backgroundTexture, tileCoords) * backgroundTextureAmount;
	vec4 rTextureColor = texture(rTexture, tileCoords) * blendMapColor.r;
	vec4 gTextureColor = texture(gTexture, tileCoords) * blendMapColor.g;
	vec4 bTextureColor = texture(bTexture, tileCoords) * blendMapColor.b;
	vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitToCamera = normalize(toCameraVector);
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	
	for(int i = 0; i < 4; i++)
	{
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);
		float nDot1 = dot(unitNormal,unitLightVector);
		float brightness = max(nDot1,0.3);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitToCamera);
		specularFactor = max(specularFactor,0.0);
		float dampedFactor = pow(specularFactor,shineDamper);
		totalDiffuse = totalDiffuse + (brightness * lightColor[i]) / attFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i]) / attFactor;
	}
	
	totalDiffuse = max(totalDiffuse, 0.2) * lightFactor;
	

		 out_Color = vec4(totalDiffuse,1.0) * totalColor + vec4(totalSpecular,1.0);
		 out_Color = mix(vec4(skyColor,1.0),out_Color,visibility);




}
