import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snt.inmemantlr.GenericParser;
import org.snt.inmemantlr.exceptions.CompilationException;
import org.snt.inmemantlr.exceptions.IllegalWorkflowException;
import org.snt.inmemantlr.listener.DefaultTreeListener;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertTrue;

public class TestHtml {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestHtml.class);

    private static File [] gfiles =  new File [] {
            new File("../html/HTMLLexer.g4"),
            new File("../html/HTMLParser.g4")
    };

    private static File [] ok = new File("../html/examples").listFiles(new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.isFile();
        }
    });

    @Test
    public void test() {

        GenericParser gp = null;
        try {
            gp = new GenericParser(gfiles);
        } catch (FileNotFoundException e) {
            assertTrue(false);
        }

        DefaultTreeListener dt = new DefaultTreeListener();

        gp.setListener(dt);

        boolean compile;
        try {
            gp.compile();
            compile = true;
        } catch (CompilationException e) {
            compile = false;
        }

        assertTrue(compile);

        for(File f : ok) {
            LOGGER.info("parse {}", f.getAbsoluteFile());
            try {
                try {
                    gp.parse(f);
                } catch (FileNotFoundException e) {
                    Assert.assertTrue(false);
                }
            } catch (IllegalWorkflowException e) {
                Assert.assertTrue(false);
            }
        }
    }


}
