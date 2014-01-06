package foo.bar.test;

import foo.bar.TestServlet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import static org.hamcrest.CoreMatchers.is;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class WLPTest {
    
    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(TestServlet.class);
    }
    
    @RunAsClient
    @Test
    public void test(@ArquillianResource URL baseUrl) throws Exception {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new URL(baseUrl, "testServlet?name=World").openStream()))) {
            assertThat(reader.readLine(), is("Hello World"));
        }
    }
}
