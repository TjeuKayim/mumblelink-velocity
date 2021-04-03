import com.github.tjeukayim.mumblelinkvelocity.MumblePlugin;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestMumbleLinkFabricCompat {
    @Test
    public void packet() {
        String host = "example.com";
        int port = -1;
        System.out.println(Arrays.toString(MumblePlugin.craftPacket(host, port)));
    }
}
