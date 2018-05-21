package audio;

import static org.lwjgl.openal.AL10.*;

public class Source
{
	
	private int sourceID;
	
	public Source()
	{
		sourceID = alGenSources();
	}
	
	public void play(int buffer)
	{
		alSourcei(sourceID, AL_BUFFER, buffer);
		alSourcePlay(sourceID);
	}
	
	public void delete()
	{
		stop();                                                                              
		alDeleteSources(sourceID);
	}
	
	public void pause()
	{
		alSourcePause(sourceID);
	}
	
	public void unpause()
	{
		alSourcePlay(sourceID);
	}
	
	public void stop()
	{
		alSourceStop(sourceID);
	}
	
	public void setLooping(boolean loop)
	{
		alSourcei(sourceID, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
	}
	
	public boolean isPlaying()
	{
		return alGetSourcei(sourceID, AL_SOURCE_STATE) == AL_PLAYING;
	}
	
	public void setVelocity(float x, float y, float z)
	{
		alSource3f(sourceID, AL_VELOCITY, x, y, z);
	}
	
	public void setVolume(float volume)
	{
		alSourcef(sourceID, AL_GAIN, volume);
	}
	
	public void setPitch(float pitch)
	{
		alSourcef(sourceID, AL_PITCH, pitch);
	}
	
	public void setPosition(float x, float y, float z)
	{
		alSource3f(sourceID, AL_POSITION, x, y, z);
	}

}
