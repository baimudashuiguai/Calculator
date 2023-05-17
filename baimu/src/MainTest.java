import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void solve()
    {
        String sum = Main.Solve("11+22");
        Assert.assertEquals("11+22=33", sum);
    }
}