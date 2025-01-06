package github.engine.legacy;

import io.github.engine.Song;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SongTest {

    @Test
    public void testInstantiateClip(){
        Song song = new Song("presets/MusicaDeTesteo.wav");
        song.instantiateClip(new File("presets/MusicaDeTesteo.wav"));
    }
}
